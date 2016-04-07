#CC_Reader system build and deploy script

#make database
rm ~/es_module.db
sqlite3 ~/es_module.db < new_db_sqlite.sql

if [ "$#" -ne 0 ]; then

	#start local card reader app
	xterm -n "Card_Reader" -e java -jar Card_Reader.jar $1 &

else

	#start local card reader app
	xterm -n "Card_Reader" -e java -jar Card_Reader.jar &

fi

#copy mave data to webapp directory
cp -r CC_Reader_REST ../webapps
cp CC_Reader_REST.war ../webapps

#start jetty server
cd ..
java -jar start.jar