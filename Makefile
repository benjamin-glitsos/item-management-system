include .env
export

start-all: clean start

start:
	@echo "$(PROJECT_ABBREV): Starting  $(c)"
	docker-compose up $(c)

stop:
	@echo "$(PROJECT_ABBREV): Killing $(c)"
	docker-compose kill $(c)

ssh:
	@echo "$(PROJECT_ABBREV): SSHing into $(c)"
	docker exec -it $(c) /bin/bash

clean:
	@echo "$(PROJECT_ABBREV): Cleaning $(c)"
	docker rm -f -v database-postgresql || true

rebuild:
	@echo "$(PROJECT_ABBREV): Rebuilding  $(c)"
	docker-compose build --no-cache $(c)

list:
	@echo "$(PROJECT_ABBREV): Listing docker objects"
	docker container ls
	@echo ""
	docker image ls
	@echo ""
	docker volume ls
