#based on PostgreSQL
FROM postgres:latest

#copy init.sql to the vm entry point > create Database
COPY init.sql /docker-entrypoint-initdb.d/

#expose the virtual PostgreSQL-Port
EXPOSE 5432

#default command to run PostgreSQL on container startup
CMD ["postgres"]