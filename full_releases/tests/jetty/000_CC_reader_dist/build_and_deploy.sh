#CC_Reader system build and deploy script

#make database
rm ~/es_module_tests.db
sqlite3 ~/es_module_tests.db < new_db_sqlite_tests.sql

#start local card reader app
xterm -n "Card_Reader" -e java -jar Card_Reader_4Test.jar &

#copy mave data to webapp directory
cp -r CC_Local_Server-1 ../webapps
cp CC_Local_Server-1.war ../webapps
cp Rester-1.war ../webapps 
cp -r Rester-1 ../webapps

#start jetty server
cd ..
java -jar start.jar