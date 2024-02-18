let board;
let boardWidth = 360;
let boardHeight = 640;
let context;

let birdWidth = 34;
let birdHeight = 24;
let birdX = boardWidth / 8;
let birdY = boardHeight / 2;
let birdImg;

let bird = {
  x: birdX,
  y: birdY,
  width: birdWidth,
  height: birdHeight,
};

let pipeArray = [];
let pipeWidth = 64;
let pipeHeight = 512;
let pipeX = boardWidth;
let pipeY = 0;

let topPipeImg;
let bottomPipeImg;

let velocityX = -1;
let velocityY = 0;
let gravity = 0.15;

let gameOver = false;
let score = 0;

let waitingScreen = true;
let gameStarted = false;

let highScore = 0;

let gameMode = 'normal';

const GAME_MODES = {
  normal: { velocityX: -1, gravity: 0.15 },
  medium: { velocityX: -1.5, gravity: 0.12 },
  hard: { velocityX: -2, gravity: 0.2 },
};

window.onload = function () {
  board = document.getElementById('board');
  board.height = boardHeight;
  board.width = boardWidth;
  context = board.getContext('2d');

  birdImg = new Image();
  birdImg.src = '/flappybird/images/flappybird.png';
  birdImg.onload = function () {
    context.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height);
  };

  topPipeImg = new Image();
  topPipeImg.src = '/flappybird/images/toppipe.png';

  bottomPipeImg = new Image();
  bottomPipeImg.src = '/flappybird/images/bottompipe.png';

  if (localStorage.getItem('highScore')) {
    highScore = parseInt(localStorage.getItem('highScore'));
  }

  changeGameMode('medium');
  requestAnimationFrame(waiting);
  requestAnimationFrame(update);
  setInterval(placePipes, 1500);

  document.addEventListener('keydown', moveBird);

  const normalModeButton = document.getElementById('normalModeButton');
  normalModeButton.addEventListener('click', function () {
    changeGameMode('normal');
  });

  const infiniteModeButton = document.getElementById('mediumModeButton');
  infiniteModeButton.addEventListener('click', function () {
    changeGameMode('medium');
  });

  const challengeModeButton = document.getElementById('hardModeButton');
  challengeModeButton.addEventListener('click', function () {
    changeGameMode('hard');
  });
};

function update() {
  if (gameStarted) {
    requestAnimationFrame(update);
  }

  if (gameOver) {
    return;
  }
  context.clearRect(0, 0, board.width, board.height);

  velocityY += gravity;
  bird.y = Math.max(bird.y + velocityY, 0);

  if (gameStarted) {
    context.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height);
  }

  if (bird.y > board.height) {
    gameOver = true;
  }

  for (let i = 0; i < pipeArray.length; i++) {
    let pipe = pipeArray[i];
    pipe.x += velocityX;
    context.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height);

    if (!pipe.passed && bird.x > pipe.x + pipe.width) {
      score += 0.5;
      pipe.passed = true;
    }

    if (detectCollision(bird, pipe)) {
      gameOver = true;
    }

    if (score > highScore) {
      highScore = score;
      localStorage.setItem('highScore', highScore);
    }
  }

  while (pipeArray.length > 0 && pipeArray[0].x < -pipeWidth) {
    pipeArray.shift();
  }

  context.fillStyle = 'white';
  context.font = '20px sans-serif';
  context.fillText('Điểm: ' + score, 5, 45);
  context.fillText('Điểm cao nhất: ' + highScore, 5, 90);
  context.fillText('Chế độ chơi: ' + gameMode, 5, 135);

  if (gameOver) {
    context.fillText('GAME OVER', 5, 180);
    context.fillText('Bấm ^ để bắt đầu lại', 5, 225);
  }
}

function placePipes() {
  if (gameOver) {
    return;
  }

  let randomPipeY = pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2);
  let openingSpace = board.height / 4;

  let topPipe = {
    img: topPipeImg,
    x: pipeX,
    y: randomPipeY,
    width: pipeWidth,
    height: pipeHeight,
    passed: false,
  };
  pipeArray.push(topPipe);

  let bottomPipe = {
    img: bottomPipeImg,
    x: pipeX,
    y: randomPipeY + pipeHeight + openingSpace,
    width: pipeWidth,
    height: pipeHeight,
    passed: false,
  };
  pipeArray.push(bottomPipe);
}

function moveBird(e) {
  if (e.code == 'ArrowUp') {
    if (waitingScreen) {
      waitingScreen = false;
      gameStarted = true;
    }

    velocityY = -4;

    if (gameOver) {
      bird.y = birdY;
      pipeArray = [];
      score = 0;
      gameOver = false;
    }
    bird.y += 10;
  }
}

function detectCollision(a, b) {
  return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
}

function waiting() {
  if (gameOver) {
    return;
  }

  context.clearRect(0, 0, board.width, board.height);

  context.fillStyle = 'red';
  context.font = '20px sans-serif';
  context.fillText('Bấm ^ để bắt đầu chơi', 90, board.height / 2);

  if (waitingScreen) {
    requestAnimationFrame(waiting);
  } else {
    requestAnimationFrame(update);
  }
}

function changeGameMode(mode) {
  gameMode = mode;
  velocityX = GAME_MODES[mode].velocityX;
  gravity = GAME_MODES[mode].gravity;
}
