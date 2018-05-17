<!-- start header -->
<header>
<div class="container ">
<div class="row nomargin">
<div class="span12">
<div class="headnav">
<ul>
<?php
    if (isset($_SESSION['login'])) {
        if ($_SESSION['login'] == "TRUE") {
            echo '<li><a>Welcome, ' .$username. '</a></li>';
            echo '<li><a href="/logout.php">Logout</a></li>';
        }
    }
    else {
        echo '<li><a href="#mySignup" data-toggle="modal"><i class="icon-user"></i> Sign up</a></li>';
        echo '<li><a href="#mySignin" data-toggle="modal">Cyclist Sign in</a></li>';
        echo '<li><a href="#mySignin2" data-toggle="modal">Family Sign in</a></li>';
    }
?>

</ul>
</div>
<!-- Signup Modal -->
<div id="mySignup" class="modal styled hide fade" tabindex="-1" role="dialog" aria-labelledby="mySignupModalLabel" aria-hidden="true">
<div class="modal-header">
<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
<h4 id="mySignupModalLabel">Create an <strong>account</strong></h4>
</div>
<div class="modal-body">
<form class="form-horizontal" action="register.php" method="post">
<div class="control-group">
<label class="control-label" for="inputSignupFname">First Name</label>
<div class="controls">
<input name="firstname" type="text" id="inputSignupFname" placeholder="First Name" required pattern=".{3,}" title="3 characters minimum">
</div>
</div>
<div class="control-group">
<label class="control-label" for="inputSignupLname">Last Name</label>
<div class="controls">
<input name="lastname" type="text" id="inputSignupLname" placeholder="Last Name" required pattern=".{3,}" title="3 characters minimum">
</div>
</div>
<div class="control-group">
<label class="control-label" for="inputSignupPhoneNumber">Phone Number</label>
<div class="controls">
<input name="phone" type="text" id="inputSignupPhoneNumber" placeholder="Phone Number" required pattern="[0-9]{8}" title="Enter Valid Mobile Number">
</div>
</div>
<div class="control-group">
<label class="control-label" for="inputSignupEmail">Email</label>
<div class="controls">
<input name="email" type="email" id="inputSignupEmail" placeholder="Email" required title="enter a valid email address">
</div>
</div>
<div class="control-group">
<label class="control-label" for="inputSignupUsername">Username</label>
<div class="controls">
<input name="username" type="text" id="inputSignupUsername" placeholder="Username" required pattern=".{6,}" title="6 characters minimum">
</div>
</div>
<div class="control-group">
<label class="control-label" for="inputSignupPassword">Password</label>
<div class="controls">
<input name="password" type="password" id="inputSignupPassword" placeholder="Password" required pattern=".{4,10}"  title="between 4 and 10 alphanumeric characters">
</div>
</div>
<div class="control-group">
<label class="control-label" for="inputSignupPassword2">Confirm Password</label>
<div class="controls">
<input name="password2" type="password" id="inputSignupPassword2" placeholder="Password" required>
</div>
</div>
<div class="control-group">
<div class="controls">
<input name="register" type="hidden" value="web">
<button type="submit" class="btn">Sign up</button>
</div>
<p class="aligncenter margintop20">
Already have an account? <a href="#mySignin" data-dismiss="modal" aria-hidden="true" data-toggle="modal">Sign in</a>
</p>
</div>
</form>
</div>
</div>
<!-- end signup modal -->
<!-- Sign in Modal -->
<div id="mySignin" class="modal styled hide fade" tabindex="-1" role="dialog" aria-labelledby="mySigninModalLabel" aria-hidden="true">
<div class="modal-header">
<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
<h4 id="mySigninModalLabel">Login to your <strong>Cyclist account</strong></h4>
</div>
<div class="modal-body">
<form class="form-horizontal" action="login.php" method="post">
<div class="control-group">
<label class="control-label" for="inputText">Username</label>
<div class="controls">
<input name="username" type="text" id="inputText" placeholder="Username" required> 
</div>
</div>
<div class="control-group">
<label class="control-label" for="inputSigninPassword">Password</label>
<div class="controls">
<input name="password" type="password" id="inputSigninPassword" placeholder="Password" required>
</div>
</div>
<div class="control-group">
<div class="controls">
<input name="login" type="hidden" value="web">
<input name="type" type="hidden" value="cyclist"> 
<button type="submit" class="btn">Sign in</button>
</div>
<p class="aligncenter margintop20">
Forgot password? <a href="#myReset" data-dismiss="modal" aria-hidden="true" data-toggle="modal">Reset</a>
</p>
</div>
</form>
</div>
</div>
<!-- end signin modal -->
<!-- Sign in Modal -->
<div id="mySignin2" class="modal styled hide fade" tabindex="-1" role="dialog" aria-labelledby="mySigninModalLabel" aria-hidden="true">
<div class="modal-header">
<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
<h4 id="mySigninModalLabel">Login to your <strong>Family account</strong></h4>
</div>
<div class="modal-body">
<form class="form-horizontal" action="login.php" method="post">
<div class="control-group">
<label class="control-label" for="inputText">Username</label>
<div class="controls">
<input name="username" type="text" id="inputText" placeholder="Username" required> 
</div>
</div>
<div class="control-group">
<label class="control-label" for="inputSigninPassword">Password</label>
<div class="controls">
<input name="password" type="password" id="inputSigninPassword" placeholder="Password" required>
</div>
</div>
<div class="control-group">
<div class="controls">
<input name="login" type="hidden" value="web">
<input name="type" type="hidden" value="family"> 
<button type="submit" class="btn">Sign in</button>
</div>
<p class="aligncenter margintop20">
Forgot password? <a href="#myReset" data-dismiss="modal" aria-hidden="true" data-toggle="modal">Reset</a>
</p>
</div>
</form>
</div>
</div>
<!-- end signin modal -->
<!-- Reset Modal -->
<div id="myReset" class="modal styled hide fade" tabindex="-1" role="dialog" aria-labelledby="myResetModalLabel" aria-hidden="true">
<div class="modal-header">
<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
<h4 id="myResetModalLabel">Reset your <strong>password</strong></h4>
</div>
<div class="modal-body">
<form class="form-horizontal">
<div class="control-group">
<label class="control-label" for="inputResetEmail">Email</label>
<div class="controls">
<input type="email" id="inputResetEmail" placeholder="Email" required>
</div>
</div>
<div class="control-group">
<div class="controls">
<button type="submit" class="btn">Reset password</button>
</div>
<p class="aligncenter margintop20">
We will send instructions on how to reset your password to your inbox
</p>
</div>
</form>
</div>
</div>
<!-- end reset modal -->
</div>
</div>
<div class="row">
<div class="span4">
<div class="logo">
<a href="/index"><img src="/img/logo.png?v=2" alt="" class="logo" height="21" width="200" /></a>
<h1>Provide A Better Cycling Experience</h1>
</div>
</div>
<div class="span8">
<div class="navbar navbar-static-top">
<div class="navigation">
<nav>
<ul class="nav topnav">
<li class=" <?php if($_GET['path'] == index) echo 'active'; ?> ">
<a href="/index">Home </a>
</li>
<li class=" <?php if($_GET['path'] == history) echo 'active'; ?> ">
<a href="/history">Your Activities History</a>
</li>
<li class=" <?php if($_GET['path'] == location) echo 'active'; ?>">
<a href="/location">location of mobile </a>
</li>
</ul>
</nav>
</div>
<!-- end navigation -->
</div>
</div>
</div>
</div>
</header>
<!-- end header -->
</ul>
</nav>
<br>
