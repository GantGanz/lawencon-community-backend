<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style type="text/css">
        body {
			background-color: #ebf4fa;
			display: flex;
			justify-content: center;
			flex-wrap: wrap;
			width: 100%;
		}

		.container-parent {
			width: 500px;
			text-align: center;
			border-radius: 40px;
		}

		.logo {
			width: 70px;
		}

		.body-email {
			background-color: white;
			text-align: left;
			padding: 20px 20px;
			border-top: 1px solid #d3d1cc;
		}

		.title {
			background-color: white;
			padding-top: 5px;
			padding-bottom: 2px;
			font-size: 25px;
			font-weight: 500;
		}

		.footer {
			height: 30px;
			text-align: center;
			padding-top: 8px;
			font-size: 15px;
			color: white;
			background-color: #1089FF;
		}
    </style>
</head>

<body>
    <div class="container-parent">
        <div class="title">
            <img class="logo" src="assets/logo.png" alt="zenith-logo">
            <p>Zenith Verification Code</p>
        </div>
        <div class="body-email">
            <h3>To: ${email}</h3>
            <p>Your verification code :</p>
            <h1>${code}</h1>
            <p>This code will expire in <b>24 hours</b></p>
            <p>Thank You</p>
        </div>
        <div class="footer">@Copyright - Zenith</div>
    </div>
</body>

</html>