<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- Declaration of language and apis to be used in html -->
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/indexLOG.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <script src="https://kit.fontawesome.com/f8537b25a5.js" crossorigin="anonymous"></script>

        <!-- Page title -->
        <title>ClosedSea</title>
    </head>
    <!-- Body declaration --->
    <body>
        <!-- Header declaration with css file --->

        <header class="header">
            <!--NavBar declaration and functions-->
            <nav class="nav">
                <!-- Navbar logo and avatar logo --->
                <img src="./Resources/closedsea%20favicon.png" class="favicon">
                <img src="./Resources/UserAvatar.png" class="nav-avatar">
                <!-- Navbar text logo --->
                <a href="#" class="logo nav-link">ClosedSea</a>
                <!-- Navbar made with an ordered list and within that list an unordered list --->
                <ul class="nav-menu">
                    <li class="nav-menu-item ">
                        <a href="create-collection.html" class="nav-menu-link nav-link">Create Colection</a>
                    </li>
                    <li class="nav-menu-item ">
                        <a href="create-nft-piece.html" class="nav-menu-link nav-link">Create NFT</a>
                    </li>
                    <li class="nav-menu-item nav-menu-login">
                        <a href="#" class="nav-menu-link nav-link"><%= request.getAttribute("username")%>
                        </a>
                    </li>
                    <li class="nav-menu-item ">
                        <a href="#" class="nav-menu-link nav-link"><%= request.getAttribute("coins")%>
                        </a>
                    </li>
                </ul>
            </nav>
        </header>
        <!-- Nft main page and main div with information -->
        <div class="bg-img"></div>

        <div class="img-prom">
            <br>
            <h1 class="h1-img-prom">Discover collect, and sell</h1>
            <h1 class="h1-img-prom">extraordinary NTFs</h1>
            <br>
            <p class="p-img-prom">OpenSea is the world's first and</p>
            <p class="p-img-prom">largest NFT marketplace</p>
            <br>
            <br>
            <a href="explore.html">
                <button class="explore-btn"> Explore <i class="fa-solid fa-magnifying-glass"></i></button>
            </a>

            <a href="wallet.html">
                <button class="create-btn"> Wallet <i class="fa-brands fa-bitcoin"></i></button>
            </a>
            <br><br>
            <a href="index.html">
                <button class="logout-btn"> Log out <i class="fa-solid fa-door-open"></i></button>
            </a>
        </div>
        <!-- Nft main page image -->
        <div class="img-body">
            <div class="image">

                <img src="./Resources/Nft1.png" alt="">
            </div>
            <div class="img-foot">
                <h1 class="h1-img-foot"> 7777 </h1>
                <h1 class="h12-img-foot"> trueeh </h1>
                <img src="./Resources/ape%20club-modified.png" class="img-author">
                <img src="./Resources/Verified-badge.png" class="verified-badge">
            </div>
        </div>
    </body>

    <!-- Page footer -->
    <footer>
        <div class="footer-content">
            <h3> ClosedSea </h3>
            <p>Repository Link</p>
            <ul class="members">
                <li>
                    <a href="https://github.com/trueeh-slave/Taller2_Programacion.git"><i class="fab fa-github"></i></a>
                </li>
            </ul>
        </div>
        <div class="footer-bottom">
            <p>Copyright &copy;2022 ClosedSea</p>
        </div>
    </footer>

</html>