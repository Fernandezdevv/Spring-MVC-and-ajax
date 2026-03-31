$(document).ready(function () {

   moment.locale('pt-br');

   var table = $("#table-server").DataTable({
      processing: true,
      serverSide: true,
      responsive: true,
      lengthMenu: [10, 15, 20, 25],
      ajax: {
         url: "/promocao/datatables/server",
         data: "data",
         beforeSend: function() {
            $("span").closest('.error-span').remove();
            $(".is-invalid").removeClass("is-invalid");
         },
      },
      columns: [
         {data: 'id'},
         {data: 'titulo'},
         {data: 'site'},
         {data: 'linkPromocao'},
         {data: 'descricao'},
         {data: 'linkImagem'},
         {data: 'preco', render: $.fn.dataTable.render.number('.', ',', 2, 'R$ ')},
         {data: 'likes'},
         {data: 'dtCadastro', render: function (dtCadastro) {
               return moment(dtCadastro).format('LLL');
            }},
         {data: 'categoria.titulo'},
      ],
      dom: 'Bfrtip',
      buttons: [
         {
            text: 'Editar',
            attr: { id: 'btn-editar', type: 'button' },
            className: 'btn btn-primary disabled', // Começa desabilitado
            enabled: false,
            action: function (e, dt, node, config) {
               if (isSelectRow()) {
                  var id = getPromoId();

                  // Faltava esse Ajax aqui para preencher o formulário!
                  $.ajax({
                     method: "GET",
                     url: "/promocao/edit/" + id, // Verifique se a barra / está certa
                     success: function (data) {
                        // Preenchendo os campos do Modal de Edição
                        $("#edt_id").val(data.id);
                        $("#edt_site").text(data.site);
                        $("#edt_titulo").val(data.titulo);
                        $("#edt_descricao").val(data.descricao); // Não esqueça da descrição
                        $("#edt_preco").val(data.preco.toLocaleString('pt-BR', {
                           minimumFractionDigits: 2,
                           maximumFractionDigits: 2
                        }));
                        $("#edt_categoria").val(data.categoria.id);
                        $("#edt_linkImagem").val(data.linkImagem);
                        $("#edt_imagem").attr('src', data.linkImagem);

                        // Só mostra o modal DEPOIS que os dados chegarem
                        $("#modal-form").modal('show');
                     },
                     beforeSend: function (){
                         $("span").closest('.error-span').remove();
                     $(".is-invalid").removeClass("is-invalid");
                     },
                     error: function() {
                        alert('Erro ao carregar dados para edição.');
                     }
                  });
               }
            }
         },
         {
            text: 'Excluir',
            attr: { id: 'btn-excluir', type: 'button' },
            className: 'btn btn-danger disabled', // Começa desabilitado
            enabled: false,
            action: function (e, dt, node, config) {
               var data = dt.row({ selected: true }).data();

               if (isSelectRow() ){
                  var id = getPromoId();

                  $.ajax({

                     method: "GET",
                     url: "/promocao/edit" + id,
                     beforeSend: function (){
                        $("#modal-delete").modal('show');
                     },
                     success: function (data){
                        $("#edt_id").val(data.id)
                        $("#edt_site").text(data.site)
                        $("#edt_titulo").val(data.titulo)
                        $("#edt_preco").val(data.preco.toLocaleString('pt-BR',{
                           minimumFractionDigits: 2,
                           maximumFractionDigits: 2

                        } ));
                        $("#edt_categoria").val(data.categoria.id);
                        $("#edt_linkImagem").val(data.linkImagem);
                        $("#edt_Imagem").attr('src', data.linkImagem);
                     },
                     error: function (){
                        alert('ocorreu algum erro tente novamemte');
                     }
                  })
               }
            }
         }
      ]
   });

   // Lógica para selecionar a linha e habilitar/desabilitar botões
   $("#table-server tbody").on('click', 'tr', function () {
      if ($(this).hasClass('selected')) {
         $(this).removeClass('selected');
         table.buttons().disable();
      } else {
         $('tr.selected').removeClass('selected');
         $(this).addClass('selected');
         table.buttons().enable();
      }
   });

   $("#btn-del-modal").on('click', function (){
      var id = getPromoId();
      $.ajax({
         method: "GET",
         url: "/promocao/delete/" + id,
         success: function (){
            $("#modal-delete").modal('hide');
            table.ajax.reload();

         },
         error: function (){
            alert("Ocorreu um erro tente mais tarde")
         }
      })
   })

   $("#btn-edit-modal").on("click", function () {
      var promo = {};
      promo.id = $("#edt_id").val();
      promo.titulo = $("#edt_titulo").val();
      promo.descricao = $("#edt_descricao").val();
      promo.linkImagem = $("#edt_linkImagem").val();
      var valorRaw = $("#edt_preco").val();

      var valorRaw = $("#edt_preco").val();

      // 1. Remove TUDO que não é número nem vírgula
      var apenasNumerosEVirgula = valorRaw.replace(/[^\d,]/g, "");

      // 2. Troca vírgula por ponto
      var precoFinal = apenasNumerosEVirgula.replace(",", ".");

      // 3. Segurança extra: remove qualquer traço de negativo que tenha sobrado
      promo.preco = Math.abs(parseFloat(precoFinal));

      promo.categoria = $("#edt_categoria").val()

      promo.categoria = $("#edt_categoria").val();

      $.ajax({
         method: "POST",
         url: "/promocao/edit",
         data: promo,
         beforeSend: function() {
            // Limpa mensagens de erro antes de tentar salvar
            $(".error-span").remove();
            $(".is-invalid").removeClass("is-invalid");
         },
         success: function () {
            $("#modal-form").modal("hide");
            table.ajax.reload(); // Recarrega a tabela para mostrar a mudança
         },
         statusCode: {
            422: function (xhr) {
               var errors = $.parseJSON(xhr.responseText);
               $.each(errors, function (key, val) {
                  $("#edt_" + key).addClass("is-invalid");
                  $("#error-" + key).addClass("invalid-feedback").append("<span class='error-span'>" + val + "</span>");
               });
            }
         },

      });
   });

   $("#edt_linkImagem").on("change", function (){
      var link = $(this).val();
      $("#edt_imagem").attr("src", link);
   })




   function getPromoId(){
      return table.row(table.$('tr.selected')).data().id;
   }

   function  isSelectRow() {

      var trow = table.row(table.$('tr.selected'));
      return trow.data() !== undefined;
   }
});