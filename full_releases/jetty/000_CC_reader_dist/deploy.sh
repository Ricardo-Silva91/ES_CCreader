if [ "$#" -ne 0 ]; then

	#start local card reader app
	xterm -n "Card_Reader" -e java -jar Card_Reader.jar $1 &
else

	#start local card reader app
	xterm -n "Card_Reader" -e java -jar Card_Reader.jar &

fi

#start jetty server
cd ..
java -jar start.jar