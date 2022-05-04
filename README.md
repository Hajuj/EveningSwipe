**Backend:**

Das backend befindet sich in der directory user_demo_postgres/.

Es wird eine postgresql datenbank benötigt welche in application.properites konfiguriert werden muss:

	spring.datasource.url=jdbc:postgresql://localhost:5432/<db name>

	spring.datasource.password=<password>

	spring.datasource.username=<Username>

Das Datenbankschema befindet sich im tools Ordner mit dem Namen psql_db_schema.jpg .



Test Accounts:

mit ? = [1-7]

username: user?@es.com

password: Win#nix#fer?

Backend URL : http://msp-ws2122-6.mobile.ifi.lmu.de:80/

Postgresdb server login:

user = eveningswipe_server

password = 64uq8wwmn2uN66iZyaZH



Systemd:

Sytemd file : tools/eveningswipe.service

cp to /etc/systemd/system/

sudo systemctl daemon-reload

sudo systemctl enable eveningswipe.service

sudo systemctl start eveningswipe.service

In der directory tools/ befinden sich Hilfsscripte zur Datenbankerstellung.

Folgende daten wurden genutzt um die datenbank mit Hilfe der scripte zu erstellen:

https://datasets.imdbws.com/

title.basics.tsv.gz

title.ratings.tsv.gz

Zum builden des Backends in /user_demo_postgres:

mvn clean package

user_demo_postgres/target/user_demo_postgres-0.0.1-SNAPSHOT.jar auf den server kopieren 


cp ~/user/demo/eveningswipe /opt/eveningswipe

oder 

/bin/java  -jar user_demo_postgres-0.0.1-SNAPSHOT.jar




**Frontend:**

Quellen App Images:

- Plus Icon:  [**https://www.google.com/search?q=plus+icon&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjh37uknYn1AhXxhf0HHfUGCzUQ_AUoAXoECAEQAw&biw=1121&bih=823&dpr=2#imgrc=YFoxZb6pht1oSM**](https://www.google.com/search?q=plus+icon&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjh37uknYn1AhXxhf0HHfUGCzUQ_AUoAXoECAEQAw&biw=1121&bih=823&dpr=2#imgrc=YFoxZb6pht1oSM)

- Gruppe Icon: [**https://wikimania2014.wikimedia.org/wiki/File:Group_font_awesome.svg**](https://wikimania2014.wikimedia.org/wiki/File:Group_font_awesome.svg)

- Search Icon: [**https://commons.wikimedia.org/wiki/File:Search_Icon.svg**](https://commons.wikimedia.org/wiki/File:Search_Icon.svg)

- Kino-Hintergrund: **https://www.vectorstock.com/royalty-free-vector/scene-cinema-background-art-performance-on-stage-vector-17525451**

- Icons Quelle: https://fonts.google.com/icons
