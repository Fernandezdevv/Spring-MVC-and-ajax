
$(document).ready(function (){
    $("#loader-img").hide();
    $("#fim-btn").hide();
})

// efeito infinite scroll (corrigido)
var pageNumber = 0;

$(window).scroll(function () { // Era scrool -> Corrigido para scroll
    var scrollTop = $(this).scrollTop(); // Era scroollTop -> Corrigido para scrollTop
    var conteudo = $(document).height() - $(window).height();

    console.log('scrollTop: ', scrollTop, ' | conteudo: ', conteudo);


    if (scrollTop >= conteudo - 1) {
        pageNumber++;
        setTimeout(function () {
            loadByScrollBar(pageNumber);
        }, 200);
    }
});

function loadByScrollBar(pageNumber) {
    var site = $("#autocomplete-input").val();
    $.ajax({
        method: "GET",
        url: "/promocao/list/ajax",
        data: {
            page: pageNumber,
            site: site
        },
        beforeSend: function (){
            $("#loader-img").show();
        },

        success: function (response) {

            if(response.length > 150){
                $(".album .row").fadeIn(250, function (){
                    $(this).append(response);
                });
            }
            else{
                $("#fim-btn").show();
                $("#loader-img").removeClass("loader");
            }
        },
        complete: function (){
            $("#loader-img").hide();
        },
        error: function(xhr) {
            alert("OPS, aconteceu um erro " + xhr.status + " - " + xhr.responseText);
        }
    });
}
//-------------------------------------------------- autoComplete ----------------------------------------------------------//

$("#autocomplete-input").autocomplete({
    source: function (request, response){
        $.ajax({
            method: "GET",
            url: "/promocao/site",
            data: {
              termo: request.term
            },
            success: function (result){
                response(result);
            }
        })
    }
})

$("#autocomplete-submit").on("click", function (){
    var site = $("#autocomplete-input").val();
    $.ajax({
        method: "GET",
        url: "/promocao/site/list",
        data: {
            site: site
        },
        beforeSend: function (){
            pageNumber= 0;
            $("#fim-btn").hide();
            $(".row").fadeOut(400, function (){
                $(this).empty();
            });
        },
        success: function (response){
            $(".row").fadeIn(250, function (){
                $(this).append(response)
            });
        },
        error: function (xhr){
            alert("Ops, algo deu errado: " + xhr.status + ", " + xhr.statusText);
        }

    });
});




//---------------------------------------------------ADICIONANDO LIKES ------------------------------------------------------//

$("button[id*='likes-btn-']").on("click", function (){
    var id = $(this).attr("id").split("-")[2];
    console.log("id:", id);

    $.ajax({
        method: "POST",
        url: "/promocao/like/" + id,
        success: function (response){
            $("#likes-count-" + id).text(response);
        },
        error: function (xhr){
            alert("Ops, aconteceu um erro:" + xhr.status + ", " + xhr.statusText);
        }
    })
})


//SSE//
var totalOfertas = 0; // Variável corrigida para minúsculo

$(document).ready(function() {
    init(); // Chama o init quando o documento estiver pronto
});

function init() {
    // Tenta estabelecer a conexão com o servidor
    const evtSource = new EventSource("/promocao/notificacao");

    evtSource.onopen = (event) => {
        console.log("A conexão SSE foi estabelecida com sucesso!");
    };

    evtSource.onmessage = (event) => {
        const count = parseInt(event.data); // Garante que o dado venha como número
        if (count > 0) {
            showButton(count);
        }
    };

    evtSource.onerror = (err) => {
        console.error("EventSource falhou:", err);
    };
}

function showButton(count) {
    totalOfertas += count; // Soma simples e direta

    // Mostra o botão e atualiza o texto
    $("#btn-alert").show().css("display", "block").text("Veja: " + totalOfertas + " nova(s) oferta(s)!");
}

$("#btn-alert").click(function () {
    $.ajax({
        method: "GET",
        url: "/promocao/list/ajax",
        data: {
            page: 0,
            site: ''
        },
        beforeSend: function () {
            pageNumber = 0;
            totalOfertas = 0;
            $("#fim-btn").hide();
            $("#loader-img").addClass("loader")
            $("#btn-alert").attr("style", "display: none;");
            $(".row").fadeOut(400, function () {
                $(this).empty();
            })
        },

        success: function (response, status, xhr) {
            $("#loader-img").hide();
            $(".row").fadeIn(250, function () {
                $(this).append(response)
            });
        },
        error: function (error) {
            console.log("error: ", error)
        }
    });
});