# Dockerfile

FROM openjdk:21-ea-24-oracle

WORKDIR /app
# aqui debemos asegurarnos del nombre de nuestro jar coincida

COPY demo-0.0.1-SNAPSHOT.jar app.jar

# ubicacion y nombre del wallet descomprimido
COPY demo\Wallet_B8IH4K73R4J0P543 /app/oracle_wallet
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]