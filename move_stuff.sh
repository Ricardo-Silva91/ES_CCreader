cp Card_Reader/dist/Card_Reader.jar full_releases/jetty/000_CC_reader_dist/Card_Reader.jar

#cp CC_Reader_REST/target/CC_Reader_REST.war full_releases/jetty/000_CC_reader_dist/CC_Reader_REST.war
#cp -r CC_Reader_REST/target/CC_Reader_REST/ full_releases/jetty/000_CC_reader_dist/CC_Reader_REST

cp CC_Local_Server/target/CC_Local_Server-1.war full_releases/jetty/000_CC_reader_dist/CC_Local_Server-1.war
cp -r CC_Local_Server/target/CC_Local_Server-1/ full_releases/jetty/000_CC_reader_dist/CC_Local_Server-1

cp rabbitMQ_clientTest/dist/rabbitMQ_clientTest.jar full_releases/broker/rabbitMQ_clientTest.jar 

cp Rester/target/Rester-1.war full_releases/jetty/000_CC_reader_dist 
cp -r Rester/target/Rester-1 full_releases/jetty/000_CC_reader_dist


# tests

cp Card_Reader_4Test/dist/Card_Reader_4Test.jar full_releases/tests/jetty/000_CC_reader_dist/Card_Reader_4Test.jar

cp com.tunaEnterprises_CC_Local_Server_4Test/target/CC_Local_Server-1.war full_releases/tests/jetty/000_CC_reader_dist 
cp -r com.tunaEnterprises_CC_Local_Server_4Test/target/CC_Local_Server-1/ full_releases/tests/jetty/000_CC_reader_dist 

cp rabbitMQ_clientTest_4Test/dist/rabbitMQ_clientTest_4Test.jar full_releases/tests/broker/

cp Rester_4Test/target/Rester-1.war full_releases/tests/jetty/000_CC_reader_dist 
cp -r Rester_4Test/target/Rester-1 full_releases/tests/jetty/000_CC_reader_dist