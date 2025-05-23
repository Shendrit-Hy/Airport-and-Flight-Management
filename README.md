# Sistemi për Menaxhimin e Aeroportit dhe Fluturimeve

Ky projekt është një aplikacion web full-stack që mundëson:
- Menaxhimin e aeroporteve dhe fluturimeve
- Rezervimin e biletave
- Shfaqjen e motit në kohë reale
- Funksionalitet për përdorues dhe administratorë
- Strukturë **multi-tenant** me izolim të të dhënave

---

## Teknologjitë dhe Arkitektura

### Backend (Java Spring Boot)
- Spring Boot, Spring Security, Spring Data JPA
- JWT për autentikim të sigurt
- PostgreSQL si bazë e të dhënave
- Arkitekturë me shtresa: Controller → Service → Repository → DTO

> Çdo rekord lidhet me një `tenantId`, dhe izolimi i të dhënave sigurohet përmes `TenantContext` + `Interceptor`.

### Frontend (React.js)
- React për UI dinamike
- Axios për thirrje API
- Formik + Yup për validime
- Context API për autentikim
- react-slick për parashikimin e motit në karusel

---

##  Veçori të veçanta

| Veçori                  | Përshkrimi                                                             |
|-------------------------|-------------------------------------------------------------------------|
| Moti aktual             | Merr të dhëna nga OpenWeather për lokacionin e aeroportit              |
| Rezervime biletash      | Përdoruesit mund të kërkojnë, rezervojnë dhe të zgjedhin ulëse         |
| Paneli i adminit        | Menaxhim për fluturime, aeroportet, pasagjerët, mirëmbajtjen, etj.     |
| Multi-tenancy           | Të dhënat izolohen për secilin tenant sipas `tenantId`                 |

---

## Si ta ekzekutoni projektin (lokalisht)

### Kërkesat:
- Java 17+
- Node.js + npm
- PostgreSQL
- Maven

### Hapat:

#### 1. Klononi projektin
```bash
git clone https://github.com/emri-juaj/Airport-and-Flight-Management.git

