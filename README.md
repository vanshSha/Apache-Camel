		
			
  - SHORT DESCRIPTION ABOUT APCHE CAMEL MODULES 
		
		1. Apache-Camel-RabbitMQ:
				In this module. I am sending message Apache Camel to RabbitMQ Broker .

		2. Apache-Camel-WireTap-Seda-ActiveMQ:
				In this module. I produce message then sent into ActiveMQ Broker then I consume message from ActiveMQ Broker to Receiving System and 
			
			Topic : WireTab :
					Send message A point to B point Between of A and B point. I create connection and send into C point .
				 SEDA :
					Mental Model: timer ---> seda --->direct---->end

		3. Activemq-Receiving-System-And-Aggregation-Strategy-Flow:
				   In this module. I am sending message from queue to another queue using ActiveMQ Broker and creating.txt file and into that txt.file .
		
				Topic : Aggregation
                                        I combine multiple messages based on an ID into a single message.

		4. Camel-Kafka-Multicast(EIP)
				  In this module. I am sending messages from one Kafka topic to another Kafka topic using the Kafka component in Apache Camel and Connect 
				  Monitering tool.
				Topic : I am sending message into multiple routes/ endpoints and process differently.
		
		5. Camel-Rest-DSL 
				In this module, I am implementing specialized REST API's.
	
	
  