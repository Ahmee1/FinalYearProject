<?php
    $_GET['path'] = 'service';
    include('check.php');
?>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Smart Cycling</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="Provide A Better Cycling Experence" />
        <meta name="author" content="Ming Wai CHEUNG" />

        <!-- css -->
        <link href="https://fonts.googleapis.com/css?family=Noto+Serif:400,400italic,700|Open+Sans:300,400,600,700" rel="stylesheet">
        <link href="css/bootstrap.css" rel="stylesheet" />
        <link href="css/bootstrap-responsive.css" rel="stylesheet" />
        <link href="css/fancybox/jquery.fancybox.css" rel="stylesheet">
        <link href="css/jcarousel.css" rel="stylesheet" />
        <link href="css/flexslider.css" rel="stylesheet" />
        <link href="css/style.css" rel="stylesheet" />
        <link href="css/smartcycling.css?version=2" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
         <!-- Theme skin -->
        <link href="skins/default.css" rel="stylesheet" />
        <!-- Fav and touch icons -->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="ico/apple-touch-icon-144-precomposed.png" />
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/apple-touch-icon-114-precomposed.png" />
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/apple-touch-icon-72-precomposed.png" />
        <link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png" />
        <link rel="shortcut icon" href="ico/favicon.png" />
        <title>Smart Cycling</title>

        <!-- =======================================================
        Theme Name: Flattern
        Theme URL: https://bootstrapmade.com/flattern-multipurpose-bootstrap-template/
        Author: BootstrapMade.com
        Author URL: https://bootstrapmade.com
        ======================================================= -->
    </head>
    <body>
        <?php
            $_GET['path'] = 'history';
            include('navbar.php');
        ?>
        <section id="inner-headline">
      <div class="container">
        <div class="row">
          <div class="span4">
            <div class="inner-heading">
              <h2>Activities History</h2>
            </div>
          </div>
          <div class="span8">
            <ul class="breadcrumb">
              <li><a href="./index"><i class="icon-home"></i></a><i class="icon-angle-right"></i></li>
              <li class="active">Your Activities History</li>
            </ul>
          </div>
        </div>
      </div>
    </section>
    
    <section id="content">
        <div class="container">
          <div class="row">

            <div class="span12">
              <table id="table" class="table table-hover tablesorter" style="width:100%">
                    <thead>
                      <tr>
                        <th>Date</th>
                        <th>Name</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody id="activities">
                      
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Date</th>
                        <th>Name</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
                  </table>

</div>



             <div id="map"></div>
                             
             </div>
          </div>
        </div>
    </section>
    <?php
            include('footer.php');
        ?>

<script src="js/controller.js?version=7"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBSeq5gj315XNA7YxQYrcyupj7qLI5SrfM&callback=initMapHistory"></script>

<!-- javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="js/jquery.js"></script>
<script src="js/jquery.easing.1.3.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/jcarousel/jquery.jcarousel.min.js"></script>
<script src="js/jquery.fancybox.pack.js"></script>
<script src="js/jquery.fancybox-media.js"></script>
<script src="js/google-code-prettify/prettify.js"></script>
<script src="js/portfolio/jquery.quicksand.js"></script>
<script src="js/portfolio/setting.js"></script>
<script src="js/jquery.flexslider.js"></script>
<script src="js/jquery.nivo.slider.js"></script>
<script src="js/modernizr.custom.js"></script>
<script src="js/jquery.ba-cond.min.js"></script>
<script src="js/jquery.slitslider.js"></script>
<script src="js/animate.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>

<!-- Template Custom JavaScript File -->
<script src="js/custom.js"></script>

  </body>
</html>
