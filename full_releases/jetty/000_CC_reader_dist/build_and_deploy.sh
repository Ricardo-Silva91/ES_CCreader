#CC_Reader system build and deploy script

#make database
rm ~/es_module.db
sqlite3 ~/es_module.db < new_db_sqlite.sql

if [ "$#" -ne 0 ]; then

	#start local card reader app
	xterm -n "Card_Reader" -e java -jar Card_Reader.jar $1 $2 &

else

	#start local card reader app
	xterm -n "Card_Reader" -e java -jar Card_Reader.jar &

fi

#copy mave data to webapp directory
cp -r CC_Local_Server-1 ../webapps
cp CC_Local_Server-1.war ../webapps
cp Rester-1.war ../webapps 
cp -r Rester-1 ../webapps

#start jetty server
cd ..
java -jar start.jar