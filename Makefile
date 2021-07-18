include .env
export

start-all: clean start

start:
	@echo "$(PROJECT_ABBREV): Starting  $(c)"
	docker-compose up $(c)

stop:
	@echo "$(PROJECT_ABBREV): Stopping $(c)"
	docker-compose down --remove-orphans $(c)

kill:
	@echo "$(PROJECT_ABBREV): Killing $(c)"
	docker-compose kill $(c)

ssh:
	@echo "$(PROJECT_ABBREV): SSHing into $(c)"
	@if [ -z "$(c)" ]; then\
		echo "Please specify a container by using the \"c\"c argument";\
	else
		docker exec -it $(c) /bin/bash
	fi;

clean:
	@echo "$(PROJECT_ABBREV): Cleaning $(c)"
	docker rm -f -v database-postgresql || true

rebuild:
	@echo "$(PROJECT_ABBREV): Rebuilding  $(c)"
	docker-compose up --force-recreate $(c)
