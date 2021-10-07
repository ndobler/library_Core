# DEVELOPER GAMES
# CORE DREAM TEAM
## Book API (Challenge #05 - 3scale)

Hemos publicado el API que hemos desarrollado para el [challenge #1](https://github.com/artudf/library_Core)

Podéis consultar uno de sus endpoints aquí: [https://books-library-1-user10-apicast-production.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/books/all](https://books-library-1-user10-apicast-production.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/books/all)

### Preparacion

Hemos configurado el remote y creado el secret

	oc login https://api.cluster-kc2df.kc2df.sandbox790.opentlc.com:6443 -p openshift -u user10
	
	3scale remote add 3scale-onprem "https://<secret>@user10-admin.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/" -k
	
	oc create secret generic 3scale-toolbox -n user10 --from-file="/home/user/.3scalerc.yaml"
	
Para ello antes hemos creado el secret **toolbox_token** en la consola de [3scale](https://user10-admin.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/).

### Desarrollo
	
Hemos desarrollado el [openapi](https://github.com/ndobler/library_Core/blob/main/src/main/resources/META-INF/openapi.yaml) y un [Jenkinsfile](https://github.com/ndobler/library_Core/blob/main/Jenkinsfile)
	
Hemos creado un buildconfig de la siguiente manera:
	
	oc new-build https://github.com/ndobler/3scale-challenge.git#main --name=library-core-pipeline -n user10
	
Hemos configurado sus variables de esta forma:
	
	oc set env bc/library-core-pipeline GIT_REPO="https://github.com/ndobler/library_Core.git" \
	GIT_BRANCH="main" PARAMS_OPENAPI_SPEC="src/main/resources/META-INF/openapi.yaml" \
	APP_NAME="library_core_api" \
	PRIVATE_URL="http://library-user2-quarkus-challenge.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com" \
	INSTANCE="3scale-onprem" OCP_PROJECT="user10" \
	SECRET_NAME="3scale-toolbox" TEST_ENDPOINT="/books" -n user10
	
Notar que el backend está desplegado con user2, que es donde desarrollamos challenge #1
Y ejecutado su build

## Bonus

Hemos desplegado la aplicacion nuevamente para hacer el bonus (por eso de no romperla y asegurarnos al menos una parte).

Podeis encontrarla [aqui](https://github.com/ndobler/book-api-secure)

	oc start-build library-code-pipeline -n user10

Lo hemos ejecutado unas 150.000 veces con la ayuda de Mikel y la documentación hasta terminar de depurar los errores y discrepancias. Lo bueno fue que hemos empezado perdidos y ya están conectados muchos de los puntos!! Más adelante agregaríamos más entornos (aunque no en el mismo JF, tests automáticos, etc.)


