#CC_Reader system build and deploy script

#make database
#rm ~/es_module.db
#sqlite3 ~/es_module.db < new_db_sqlite.sql

#start local card reader app
xterm -e java -jar Card_Reader.jar &

#copy mave data to webapp directory
#cp -r Card_Reader_REST ../webapps
#cp Card_Reader_REST.war ../webapps

#start jetty server
cd ..
java -jar start.jar