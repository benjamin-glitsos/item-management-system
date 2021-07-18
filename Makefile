include .env
export

all: clean start

start:
	@echo "Starting $$PROJECT_ABBREV..."
	docker-compose up $2

clean:
	@echo "Cleaning $$PROJECT_ABBREV..."
	docker rm -f -v database-postgresql || true
