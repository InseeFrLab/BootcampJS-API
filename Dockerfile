FROM adoptopenjdk:11-jre-hotspot
ADD target/phone*.jar /app/jeanmichel.jar
CMD java -jar /app/jeanmichel.jar