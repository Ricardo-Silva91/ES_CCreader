var express = require('express');
var mysql = require ("mysql");
var bodyParser = require('body-parser');

var app = express();

var connection = mysql.createConnection ({
    host : 'localhost',
    user : 'root',
    password : '',
    database : 'es_module'
});

app.use(bodyParser.urlencoded({
    extended: true
}));

var jsonParser = bodyParser.json();

connection.connect(function(err){
if(!err) {
    console.log("Database is connected ...");    
} else {
    console.log("Error connecting database ...");    
}
});

app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.post('/report', jsonParser, function (req, res) {
	var data = req.body;

	console.log(req.body);

	var person = data.person_info;

	console.log(JSON.stringify(person));

	person_data = {
            country: person.country,
            firstname: person.firstname,
            notes: person.notes,
            numBI: person.numBI,
            documentType: person.documentType,
            cardVersion: person.cardVersion,
            lastnameFather: person.lastnameFather,
            lastnameMother: person.lastnameMother,
            numSNS: person.numSNS,
            firstnameMother: person.firstnameMother,
            locale: person.locale,
            deliveryDate: person.deliveryDate,
            height: person.height,
            numSS: person.numSS,
            sex: person.sex,
            cardNumberPAN: person.cardNumberPAN,
            firstnameFather: person.firstnameFather,
            birthDate: person.birthDate,
            mrz3: person.mrz3,
            mrz2: person.mrz2,
            lastname: person.lastname,
            mrz1: person.mrz1,
            nationality: person.nationality,
            cardNumber: person.cardNumber,
            numNIF: person.numNIF,
            deliveryEntity: person.deliveryEntity     
            };

    var interaction = data.interaction_info;

    var interaction_data = {
            interaction: interaction.interaction,
            roomCode: interaction.roomCode,
            time: interaction.time,
            person_id : person.numBI
        };


    connection.query('SELECT * FROM person WHERE numBI =' + mysql.escape(person_data.numBI), 
    function(err,result){
        if(err) console.log(err);

        if(!result[0]){
        	 connection.query('INSERT INTO person SET ?', person_data, function(err,result){
        	    	if(err) console.log(err);
        	    });
        	}

    connection.query('INSERT INTO interaction SET ?', interaction_data, function(err,result){
        if(err) console.log(err);
        
        });


         });



    console.log("Received Report: ", data);

    res.status(200).send()

});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});