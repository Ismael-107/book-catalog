# 📘 Plantilla — Catálogo de Libros (Spring Boot)

Plantilla para GitHub Classroom por entregas:
1) Eco del endpoint (texto plano) → **Entrega 1**
2) JSON sin ResponseEntity → **Entrega 2**
3) Con ResponseEntity → **Entrega 3**
4) Servicio → **Entrega 4**
5) Excepciones en servicio → **Entrega 5**
6) Controller Advice global → **Entrega 6**

## Arranque
```bash
mvn spring-boot:run
```
Prueba:
```bash
curl -i http://localhost:8080/books
```

## Test inicial
```bash
mvn -q -Dtest=BookControllerTextTest test
```
