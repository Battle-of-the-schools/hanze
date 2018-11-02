setInterval(() => {
    
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "/messages.json", false);
    xhttp.send();
    
    list = JSON.parse(xhttp.responseText);

    for (var almostDeadHuman of list) {

        console.log(    );

        var divId = "message-" + almostDeadHuman.id;
        var div = document.getElementById(divId);

        var html = `
        <div class="message-item" id="${divId}">
            <div class="row">
                <div class="col-sm-9 message-colored-item">
                    <a class="title" href="/messages/${almostDeadHuman.id}">
                        Device ${almostDeadHuman.id}
                    </a>
                    <br>
                    a few minutes ago
                </div>
            </div>
        </div>
        `;


        div ? (() => {
            div.outerHTML = html;
        })() : (() => {
            document.getElementById("deadhumans").innerHTML += html;

            var redMarker = new L.DivIcon({
                className: 'help-marker red',
                html: '<div class="help-marker-inner"></div><div class="help-marker-inner2"></div>'
            })

            var markertje = L.marker([almostDeadHuman.latitude, almostDeadHuman.longitude], {icon: redMarker})
            markertje.addTo(window.map)
        })()

    }

}, 1500);
