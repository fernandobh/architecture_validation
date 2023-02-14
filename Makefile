prepare: nginx-image app-image jmeter

nginx-image: 
	cd nginx ; docker build -t nginx-proxy:1.0.0 .

app-image: 
	cd client ; docker build -t app-mongo-error:1.0.0 .

jmeter:
	curl https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.5.zip --output apache-jmeter-5.5.zip
	unzip apache-jmeter-5.5.zip

clean: 
	docker-compose -f docker-compose.yaml down
	docker-compose -f docker-compose.yaml rm

up-smallest:
	docker-compose -f docker-compose.yaml down
	docker-compose -f docker-compose.yaml rm
	JAVA_MEMORY="-Xms32m -Xmx32m" MONGO_URL="mongodb://mongoUser:mongoUser123@mongo:27017/clientdb?minPoolSize=0&maxPoolSize=2" docker-compose -f docker-compose.yaml up

up-small:
	docker-compose -f docker-compose.yaml down
	docker-compose -f docker-compose.yaml rm
	JAVA_MEMORY="-Xms64m -Xmx64m" MONGO_URL="mongodb://mongoUser:mongoUser123@mongo:27017/clientdb?minPoolSize=5&maxPoolSize=10" docker-compose -f docker-compose.yaml up

up-normal:
	docker-compose -f docker-compose.yaml down
	docker-compose -f docker-compose.yaml rm
	JAVA_MEMORY="-Xms128m -Xmx128m" MONGO_URL="mongodb://mongoUser:mongoUser123@mongo:27017/clientdb?minPoolSize=10&maxPoolSize=20" docker-compose -f docker-compose.yaml up

k6-test: 
	k6 run --vus 60 --duration 100s test-k6.js

jm-test-small:
	./apache-jmeter-5.5/bin/jmeter -n -t TestPlan.jmx -l out/result${SAIDA}.jtl

jm-test:
	./apache-jmeter-5.5/bin/jmeter -n -t TestPlan-40-60.jmx -l out/result${SAIDA}.jtl
