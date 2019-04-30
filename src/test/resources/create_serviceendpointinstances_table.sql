-- Contains the HealthMetrics which has been monitored for the ServiceEndpoints.
CREATE TABLE serviceendpointinstances (
	uuid VARCHAR(36) NOT NULL PRIMARY KEY,
	serviceendpointname VARCHAR(100) NOT NULL
)