<%@page import="java.util.*"%>
<!DOCTYPE html>
<%@ page language= "java" contentType="text/html; charset-UTF-8"
   pageEncoding="UTF-8"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<htm]>
<head>
<meta http-equive="Content-Type"  content="text/html: charset=utf-8">
<title>jwt</title>
<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/jquery-ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet"  href="webjars/bootstrap/4.5.0/css/bootstrap.min.css">

<script src="webjars/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script.


<link rel="stylesheet" href="webjars/bootstrap-select/1.13.18/css/bootstrap-select.css" />
<script src="webjars/bootstrap-select/1.13.18/js/bootstrap-select.min.js"></script>

<link rel-"stylesheet" type-"text/css" href-"../css/grid.css"  media="screen" />

<link rel="stylesheet" href="webjars/material-design-icons/4.0.0/material-icons.css" >
<link rel="stylesheet" href="webjars/font-awesome/5.15.2/css/all.min.css">

<script src="webjars/bootstrap-multiselect/1.0.0/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="webjars/bootstrap-multiselect/1.0.0/css/bootstrap-multiselect.css" >

<spring:url value="/css/main.css" var="springCss" />
<link href="${springCss}" rel="stylesheet" />

<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

<style>
  .glyphicon-pencil:before {
      content: "\270f";
   }
   .material-icons {
      font-size: 15px;
   }
   body {
      color: #404E67;
      background: #F5F7FA;
      font-family: 'Open Sans' ,sans-serif;
   }
   textarea {
      word-break: break-all;
   }
   .dropdown-toggle {
        font-size: 0.65rem;
        line-height: 1;
   }
   .bootstrap-select
   :not
   ([class
   *=
   "col-"
   ]
   )
   :not
   (
   [
   class
   *=
   "form-control"
   ]
   )
   :not
   (
   .input-group-btn
   )
   {
   width
   250
   px;
   }
   .bootstrap-select {
    width: 250px;
   }
   .dropdown-menu {
     font-size: 0.65rem;
   }
   .bs-ok-default{
    font-size: 0.65rem;
   }

   .table-wrapper {
     width: 700px;
     margin: 30px auto;
     background: #fff;
     padding: 20px;
     box-shadow: 0 1px 1px rgba(0,0,0, .05);
   }
   .table-title {
      padding-bottom: 10px;
      margin: 0 0 10px;
   }
   .table-title h2 {
       margin: 6px 0 0;
       font-size: 22px;
    }

   .table-title .add-new {
        float: right;
        height: 38px;
        font-weight: bold;
        font-size: 12px;
        text-shadow: none;
        min-width: 100px;
        border-radius: 5Opx;
        line-height: 13px;
    }
    .table-title .add-new i{
        margin-right: 4px;
    }

     div.left {
        width: 408;
        display: inline-block;
     }

     div.right {
        width: 40%;
        display: inline-block;
        vertical-align: top;
     }

     table.table {
        table-layout: fixed;
     }

     table.table tr th, table.table tr td {
        border-color: #ege9e9;
     }

     table.table th i {
        font-size: 13px;
        margin: 0 5px;
        cursor: pointer;
     }

     table.table th:last-child {
        width: 100px;
     }

     table.testgrid tr {
        height: 4px;
    }

     table.table td a {
        cursor: pointer;
        display: inline-block;
        margin: 0 5px;
        min-width: 24px;
     }

     table.table td a.add {
        color: #27C46B;
     }

     table.table td a.edit {
        color: #FFC107;
     }
     table.table td a.delete {
        color: #E34724;
     }
     table.table td i {
        font-size: 19px;
     }

     table.table td a.add i {
       font-size: 24px;
       margin-right: -1px;
       position: relative;
       top: 3px;
     }

     table.table .form-control {
        height: 32px;
        ļine-height: 32px;
        box-shadow: none;
        border-radius: 2px;
     }

     table.table .form-control.error {
        border-color: #f50000;
     }

     table.table td .add {
        display: none;
     }

     .noEditx {
        pointer-events: none;
        disabled: true;
        ccbackground: #C0C0C0;
        opacity: 0.6;
     }

     .modal {
        position: absolute;
        top: 220px;
        right: 100px;
        bottom: 0;
        left: 0;
        z-index: 10040;
        overflow: auto;
        overflow-y: auto;
      }
      .nav-tabs .nav-item.show ,nav-link, .nav-tabs .nav-link.active {
        color:#007bff;
        background-color: #ddd;
        border-color: #dee2e6 #dee2e6 #fff;
      }
</style>



     <script type="text/javascript">
        $(document).ready(function() {
            $('#Create').click(function() {
            var dataCreate = $('#dataCreate').val();
            $.ajax({
                    type: 'POST' ,
                    contentType : 'application/json' ,
                    url:  '/token',
                    data: dataCreate,
                    dataType : 'text',
                    success : function(data) {
                        $('#resultCreate').val(data);
                        $('#dataDecode').val(data);
                    },
                    error : function(xhr, status, error) {
                        var err=eval("(" + xhr.responseText +")");
                        alert(err.message);
                    }
                })
             });

            $('#Decode').click(function() {
                var dataDecode = $('#dataDecode').val();
                $.ajax({
                    type: 'POST',
                    contentType : 'application/json',
                    url: '/check_token',
                    data:dataDecode,
                    // datatype. 'json',
                    success: function(data) {
                        $('#resultDecode').val(JSON.stringify(data));
                    },
                    error : function(xhr , status, error) {
                        alert(xhr.responseText);
                    }
                  })
                });
              });
        </script>

        </head>

        <body>

          <div class="container" style="margin-top: 50px; max-width: 80%">
            <div class="starter-template">
               <h1 style="width: 100%">
                 Jwt <a style="float: right;" href-"/logout">logout </a>
               </h1>
            <br/>
              <h1 style="width: 100%">
                <a style="float: right;"> user: <%=request.getRemoteUser()%></a>
              </h1>
            </div>
           <div id="wrap" style="width: 100%">
               <h2 style="width: 100%">create jwt</h2>
               <div class="row">
               <div class="col-sm-11">
                   <textarea class="form-control" id="dataCreate"
                     style="height: 150px">here: data sent...</textarea>
               </div>
               <div class="col-sm-1">
                    <button type-"button" class="btn btn-primary add-new" id="Create">
                        <i class="fa"></i>Create
                    </button>
               </div>
              <div class="col-sm-12">
                   <textarea class="form-control" id="resultCreate"
                         style="height: 150px">result</textarea>
              </div>
            </div>
                  <h2 style="width: 100%">Decode jwt</h2>
                  <div class="row">
                        <div class="col-sm-11">
                            <textarea class="form-control" id="dataDecode"
                               style="height: 150px">here: data sent. .. </textarea>
                        </div>
                      <div class="col-sm-1">
                       <button" type="button" class="btn btn-primary add-new" id="Decode">
                          <i class-"fa "></i>Decode
                      </button>
                     </div>
                    <div class="col-sm-12">
                             <textarea class="form-control" id="resultDecode"
                          style="height: 150px">result</textarea>
                     </div>
              </div>
             </div>
         </div>
</body>