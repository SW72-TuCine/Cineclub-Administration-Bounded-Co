#FROM openjdk:17-alpine

#COPY ./target/cineclub-administration-0.0.1-SNAPSHOT.jar /

#RUN sh -c 'cineclub-administration-0.0.1-SNAPSHOT.jar'

#ENTRYPOINT ["java","-jar","/cineclub-administration-0.0.1-SNAPSHOT.jar"]

#RUN chmod +x /cineclub-administration-0.0.1-SNAPSHOT.jar

# Usa una imagen de OpenJDK como base
FROM openjdk:17-alpine

# Copia el archivo JAR a la raíz del sistema de archivos en el contenedor
COPY ./target/cineclub-administration-0.0.1-SNAPSHOT.jar /cineclub-administration-0.0.1-SNAPSHOT.jar

# Establece el comando de entrada para ejecutar el archivo JAR con Java
ENTRYPOINT ["java", "-jar", "/cineclub-administration-0.0.1-SNAPSHOT.jar"]
