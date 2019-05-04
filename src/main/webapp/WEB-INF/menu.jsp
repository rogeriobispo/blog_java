<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <a class="navbar-brand" href="/blog/app/users">Blog - PUC Minas Virtual</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="/blog/app/users">Usuários</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
            <li class="navbar-text">
            <form action="${path}/logout"  method="post">			
			<button  class="btn btn-danger" type="submit">Sair (Logout)</button>
			<input type="hidden" id="_csrf" name="_csrf" value="${_csrf.token}">
			</form>
            </li>           
        </ul> 
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>