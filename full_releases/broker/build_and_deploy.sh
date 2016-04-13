#CC_Reader system build and deploy script

#make database
rm ~/es_module_rabbit.db
sqlite3 ~/es_module_rabbit.db < db_rabbit_broker.sql

#start test broker
xterm -n "broker rabbit" -e java -jar rabbitMQ_clientTest.jar 