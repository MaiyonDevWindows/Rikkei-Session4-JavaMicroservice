docker-compose-deploy:
	cd SpringCloud
	gradle build -x test
	cd ..

	cd EurekaServer
	gradle build -x test
	cd ..

	cd AuthService
	gradle build -x test
	cd ..

	cd SpringAPIGateway
	gradle build -x test
	cd ..

	cd ProductManager
	gradle build -x test
	cd ..

	cd Order_Service
	gradle build -x test
	cd ..

	docker-compose up -d
docker-compose-stop:
	docker-compose down
docker-compose-delete:
	docker-compose down
	docker image rm spring-cloud-image
	docker image rm eureka-server-image
	docker image rm auth-services-image
	docker image rm api-gateway-image