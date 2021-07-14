include .env
export

start:
	@echo "Starting $$PROJECT_ABBREV"
	docker rm -f -v database-postgresql
	docker-compose up $2
