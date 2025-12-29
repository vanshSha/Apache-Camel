		
			
  - SHORT DESCRIPTION ABOUT APACHE CAMEL MODULES:
                        This module sends messages from Apache Camel routes to a RabbitMQ broker using the Camel  RabbitMQ component.
                        It demonstrates message publishing and basic broker integration.
		
		1. Apache-Camel-RabbitMQ:
				In this module. I am sending message Apache Camel to RabbitMQ Broker .

		2. Apache-Camel-WireTap-SEDA-ActiveMQ
				This module produces messages, sends them to an ActiveMQ broker, and then consumes them in a receiving system.
			
			Topics Covered:
			        WireTab :
                                 Sends a copy of a message to a secondary endpoint (Point C) without affecting the main flow from Point A to Point B.				 
				  SEDA :
				      Enables asynchronous, in-memory message processing.
				      Mental Model: timer ---> seda --->direct---->end

		3.ActiveMQ-Receiving-System-And-Aggregation-Strategy-Flow:
				   This module transfers messages from one ActiveMQ queue to another and writes aggregated results into a .txt file.

				Topics Covered: Aggregation:
                                             Combines multiple messages based on a common ID into a single message.
		4. Camel-Kafka-Multicast (EIP):
				  This module sends messages from one Apache Kafka topic to multiple routes using the  Multicast EIP in Apache Camel.
                             Each route processes the message differently, and monitoring tools are integrated for observability. 
				Topic: Monitering tool:
					I am sending message into multiple routes/ endpoints and process differently.
		
		5. Camel-Rest-DSL: 
                           This module implements REST APIs using Camel REST DSL, exposing integration logic over HTTP endpoints.
		6. Camel-Metrics:
                            This module implements route-level and system-level monitoring to track performance, throughput, and health of Apache Camel routes.	

		7. Camel-Integration-Labs:
			        This module contains implementations of remaining Apache Camel EIPs and concepts used for hands-on practice and experimentation.
	
	
  