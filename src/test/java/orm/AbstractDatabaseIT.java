package orm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Abstract base class for tests which use arquillian for ejb tests.
 * 
 * @author Philipp Buchholz
 */
@RunWith(Arquillian.class)
public abstract class AbstractDatabaseIT {

	/**
	 * Deploys all the ejbs located in the package
	 * {@link Gateway#getClass()#getPackage()} or one of its subpackages in a war
	 * file to the embedded container. Additionally an empty beans.xml file is
	 * deployed enabling CDI for annotated beans (Discovery = Annotated).
	 * 
	 * @return
	 */
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "orm.war").addPackages(true, Gateway.class.getPackage()) //
				.setWebXML("web.xml") //
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml") //
				.addAsWebInfResource("glassfish-resources.xml");
	}

	protected abstract DataSource getTestDataSource();

	@Before
	public void setupDatabase() throws IOException, URISyntaxException {
		DataSource dataSource = this.getTestDataSource();
		this.createDatabasePart(dataSource, "table");
	}

	private void createDatabasePart(DataSource dataSource, String databasePartName)
			throws IOException, URISyntaxException {
		Path path = Paths.get(ClassLoader.getSystemResource(".").toURI());

		Files.walk(path).filter(Files::isRegularFile).forEach((current) -> {
			String filename = current.toFile().getName();
			String endPattern = "_".concat(databasePartName).concat(".sql");
			if (filename.startsWith("create_") && filename.endsWith(endPattern)) {
				try {
					String createStatement = new String(Files.readAllBytes(current));
					System.out.println(createStatement);
					dataSource.getConnection().prepareStatement(createStatement).execute();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@After
	public void clearDatabase() throws IOException, URISyntaxException, SQLException {
		DataSource dataSource = this.getTestDataSource();
		Path path = Paths.get(ClassLoader.getSystemResource("drop_tables.sql").toURI());
		this.executeEachLineAsSqlStatement(dataSource.getConnection(), path);
	}

	private void executeEachLineAsSqlStatement(Connection connection, Path filePath) throws IOException {
		Files.readAllLines(filePath).stream().forEach((line) -> {
			try {
				connection.prepareStatement(line).execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}

}
