Justificación De Proyecto

En este documento se responderán las cuestiones relacionadas con nuestro proyecto de monitorización del ambiente y riego automático, ¿tales preguntas serán por qué se hará?, Para qué?, ¿qué problemáticas resuelve? ¿Y cómo se hará?
Con la finalidad de dar a entender mejor la realización de este proyecto y en que esta enfocado tratamos de resumir lo mejor posible las respuestas.

La necesidad de nuestro proyecto 

Principalmente se pensó en este proyecto como una forma de cuidado de una simple maceta, algo que suena tan sencillo como “mantener viva una planta” con esa idea en mente se busco la manera de realizar un monitoreo en tiempo real del entorno de esta maceta y poder así mantener una planta segura mientras esta crece en buen estado.

Finalidad del proyecto

Este proyecto comenzó principalmente como el regadío automático de una planta, Conforme fue avanzando este proyecto fuimos agregando distintos métodos de cuidado, enfocados siempre en esta idea pudimos incluir varios métodos de cuidado que una planta necesitaría para crecer fuerte y sana también analizando cuales son los riesgos que podría caber en este proceso de crecimiento haciendo lo más seguro posible.
Para resumir la finalidad de este proyecto es el ayudar a las personas que quieran cuidar de una planta, pero no tienen el suficiente conocimiento de como hacerlo o siquiera tenga el tiempo que conlleva cuidarla, para esto realizamos una aplicación que monitorea tu planta en tiempo real y que incluso puede suministrar agua para su buen cuidado.

Problemáticas que resolver

Bien se indico antes que este proyecto resuelve varias problemáticas cuanto se refiera al cuidado de una planta y no solo este, sino que intentamos verlo como una manera de ayudar más allá del monitoreo una simple maceta.
El problema que resuelve nuestro proyecto es el de evitar que tu planta o cultivo muera (evitando que se seque) y monitorizar su ambiente para detectar si esta en el lugar correcto
Con esto ofrecemos un monitoreo en tiempo real de la tierra y el entorno al que pertenece el cultivo para informar al propietario del estado de su planta.

Que exigencias conlleva

La principal exigencia que requiere este proyecto es la de realizar un monitoreo en tiempo real para esto implementamos una serie de sensores que van en el interior y exterior de la maceta a cuidar. Agregando que aparte del monitoreo también es esencial el cuidado de la planta para esto se necesitan dos cosas indispensables que serial luz y agua, la primera se puede solucionar por si sola ya que solo necesita luz solar para crecer correctamente pero la segunda, el agua no siempre se podrá regar ya que se puede olvidar o incluso regar de más, para esto implementamos una bomba de agua que se activa automáticamente cuando el sensor de humedad detecta que hace falta humedad en la tierra de cultivo así solucionando ambos factores importantes en nuestro proyecto que serian el de cuidado y monitoreo de una planta.

Todo esto está incluido en una aplicación móvil que permitirá al dueño de este maceta saber en qué estado se encuentra su planta. 

Lista de materiales: 
Sensor de humedad FC-28
Voltaje de señal de salida analógico (AO) : 0 a VCC
Voltaje de operación: 3.3V - 5V DC (VCC)
Voltaje de señal de salida analógico (AO) : 0 a VCC
Voltaje de señal de salida digital (DO) : 3.3V/5V TTL
Sensor de monóxido 
Modelo: MQ7
Rango de medición: 20 a 2000 ppm.
Voltaje de operación: 5V DC
Concentración de Oxígeno: 21%
Sensor mq135
Modelo: MQ135
Concentración de Oxígeno: 21%
Voltaje de operación: 5V DC
Detecting concentration scope¬ 10ppm-300ppm NH3 10ppm-1000ppm Benzene 10ppm-300ppm Alcohol
Sensor de temperatura y humedad ambiental:
·	Modelo: dth22
·	Alimentación: 3.3Vdc ≤ Vcc ≤ 6Vdc
·	Rango de medición de temperatura: -40°C a 80 °C
·	Precisión de medición de temperatura: <±0.5 °C
·	Resolución Temperatura: 0.1°C
·	Rango de medición de humedad: De 0 a 100% RH
·	Precisión de medición de humedad: 2% RH
·	Resolución Humedad: 0.1%RH
·	Tiempo de sensado: 2s

Esquematico: 
![Untitled](https://user-images.githubusercontent.com/80282335/204710275-be0c0df4-88b7-43de-869b-efc00f363771.png)

Código para Arduino:
https://github.com/Shiachi/regadioAutoIOT
Código para Android:
https://github.com/CristobalAlonso/AndroidRegadioIOT
