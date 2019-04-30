-- Contains the HealthMetrics which has been monitored for the ServiceEndpoints.
CREATE TABLE healthmetrics (
	uuid VARCHAR(36) NOT NULL PRIMARY KEY,
	serviceendpointinstanceuuid VARCHAR(36) NOT NULL,
	healthmetricscategory VARCHAR(50) NOT NULL,
	healthmetricsunit VARCHAR(20) NOT NULL,
	healthmetricstype VARCHAR(20) NOT NULL,
	healthmetricsvalue VARCHAR(50) NOT NULL
)