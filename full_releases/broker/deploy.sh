#CC_Reader system build and deploy script

#make database
#rm ~/es_module.db
#sqlite3 ~/es_module.db < new_db_sqlite.sql

#start test broker
xterm -n "broker rabbit" -e java -jar rabbitMQ_clientTest.jar 