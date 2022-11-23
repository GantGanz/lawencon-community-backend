<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style type="text/css">
        body {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            width: 100%;
        }

        .container-parent {
            background-color: azure;
            width: 500px;
            text-align: center;
            border-radius: 40px;
            border: 1px solid gray;
        }

        .logo {
			padding-top: 20px;
            width: 70px;
        }

        .body-email {
            text-align: left;
            padding: 20px 20px;
            border-top: 1px solid skyblue;
        }

        .title {
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
            border-bottom-left-radius: 40px;
            border-bottom-right-radius: 40px;
        }
    </style>
</head>

<body>
    <div class="container-parent">
        <div class="title">
            <img class="logo" src="https://raw.githubusercontent.com/GantGanz/zenith-backend/main/lawencon-community/src/main/resources/email-template/assets/logo.png" alt="zenith-logo">
            <p>Zenith Verification Code</p>
        </div>
        <div class="body-email">
            <h3>To: ${email}</h3>
            <p>Your verification code :</p>
            <h1>${code}</h1>
            <p>This code will expire in <b>1 hour</b></p>
            <p>Thank You</p>
        </div>
        <div class="footer">@Copyright - Zenith</div>
    </div>
</body>

</html>