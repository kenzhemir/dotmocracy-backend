Our project has two repositories:  
1. Backend: https://github.com/kenzhemir/dotmocracy-backend
2. Front-end: https://github.com/kenzhemir/dotmocracy-frontend
  
  
### How to setup from war file
1. Download dotmocracy.war file [here](https://drive.google.com/drive/folders/10jajiept3NMzR_Z0pqMTVEp9-T580W_M)
2. Install TomEE
3. Put dotmocracy.war file to the TomEE/webapps
4. Add TomEE/conf/server.xml folowing lines inside `<Host></Host>` tags:  
`<Context path="" docBase="dotmocracy" debug="0" reloadable="true"></Context>`  
Example: ![Image](https://i.imgur.com/s62mp7r.png)
5. Run the TomEE server (bin/startup), and you will be able to see the project on localhost:8080
