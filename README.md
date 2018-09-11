This is sample Spring.io project with single REST controller.
Aim of this project is to demonstrate basic Spring.io knowledge.

Background
- Marlin Shopping mobile app expose multiple data types on the home page, including user profile info, 
product categories and top offers from partners (see screenshots)

![Home page with sops](https://github.com/suntehnik/spring-kotlin-demo/blob/master/Screenshot_1536689588.png)
![Home page scrolled down to categories](https://github.com/suntehnik/spring-kotlin-demo/blob/master/Screenshot_1536689603.png)
- Each of data types has different schema
- Shop - entity for e-commerce web site, has the following properties: id, name, commission level (cpa), 
deep_link - address of the web site, logo - shop logo. All shops has mapping to category, but this mapping is not used 
on the home page and thus omitted here for simplicity.
- Category - entity for shop categories, categories also presented on home page and has following properties:
id, name, icon and number of shops in each category

Business value of the project:
- in mobile world it is crucial to keep as low number of network requests as possible
- multiple network requests tend to drain mobile battery
- this particular project demonstrates how REST controller can provide necessary information for mobile app page 
within one network request

Technological stack:
- all data is stored in Firebase Realtime DB (as per current project design)
- HomeController service subscribes to Realtime DB changes of categories and shops and caches provided data
- for each HTTP GET request service responds with actual top shops and categories list
- shops rotated every 15m or so, rotation is done on another service