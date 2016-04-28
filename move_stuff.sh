cp Card_Reader/dist/Card_Reader.jar full_releases/jetty/000_CC_reader_dist/Card_Reader.jar

#cp CC_Reader_REST/target/CC_Reader_REST.war full_releases/jetty/000_CC_reader_dist/CC_Reader_REST.war
#cp -r CC_Reader_REST/target/CC_Reader_REST/ full_releases/jetty/000_CC_reader_dist/CC_Reader_REST

cp CC_Local_Server/target/CC_Local_Server-1.war full_releases/jetty/000_CC_reader_dist/CC_Local_Server-1.war
cp -r CC_Local_Server/target/CC_Local_Server-1/ full_releases/jetty/000_CC_reader_dist/CC_Local_Server-1

cp rabbitMQ_clientTest/dist/rabbitMQ_clientTest.jar full_releases/broker/rabbitMQ_clientTest.jar 

cp Rester/target/Rester-1.war full_releases/jetty/000_CC_reader_dist 
cp -r Rester/target/Rester-1 full_releases/jetty/000_CC_reader_dist