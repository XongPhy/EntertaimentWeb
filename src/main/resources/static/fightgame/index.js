const canvas = document.querySelector('canvas');
const c = canvas.getContext('2d');

canvas.width = 1280;
canvas.height = 720;
const gravity = 0.8;

c.lineWidth = 5;
c.fillRect(0, 0, canvas.width, canvas.height);

const background = new Sprite({
  position: {
    x: 0,
    y: 0,
  },
  imageSrc: '/fightgame/images/pick1.png',
});

const player = new Fighter({
  position: {
    x: 150,
    y: 0,
  },
  velocity: {
    x: 0,
    y: 0,
  },
  imageSrc: '/fightgame/images/CUT/idle.png',
  framesMax: 6,
  scale: 2.5,
  offset: {
    x: 150,
    y: -15,
  },
  sprites: {
    idle: {
      imageSrc: '/fightgame/images/CUT/idle.png',
      framesMax: 6,
    },
    run: {
      imageSrc: '/fightgame/images/CUT/run.png',
      framesMax: 8,
    },
    jump: {
      imageSrc: '/fightgame/images/CUT/jump.png',
      framesMax: 8,
    },
    fall: {
      imageSrc: '/fightgame/images/CUT/fall.png',
      framesMax: 8,
    },
    attack: {
      imageSrc: '/fightgame/images/CUT/attack.png',
      framesMax: 8,
    },
    takeHit: {
      imageSrc: '/fightgame/images/CUT/takeHit.png',
      framesMax: 4,
    },
    death: {
      imageSrc: '/fightgame/images/CUT/dead.png',
      framesMax: 8,
    },
  },
  attackBox: {
    offset: {
      x: -110,
      y: 50,
    },
    width: 50,
    height: 100,
  },
});

const enemy = new Fighter({
  position: {
    x: 1100,
    y: 0,
  },
  velocity: {
    x: 0,
    y: 0,
  },
  color: 'blue',
  imageSrc: '/fightgame/images/Legacy/idle.png',
  framesMax: 4,
  scale: 2.5,
  offset: {
    x: 0,
    y: 0,
  },
  sprites: {
    idle: {
      imageSrc: '/fightgame/images/Legacy/idle.png',
      framesMax: 4,
    },
    run: {
      imageSrc: '/fightgame/images/Legacy/run.png',
      framesMax: 8,
    },
    jump: {
      imageSrc: '/fightgame/images/Legacy/jump.png',
      framesMax: 4,
    },
    fall: {
      imageSrc: '/fightgame/images/Legacy/fall.png',
      framesMax: 3,
    },
    attack: {
      imageSrc: '/fightgame/images/Legacy/attack.png',
      framesMax: 8,
    },
    takeHit: {
      imageSrc: '/fightgame/images/Legacy/jump.png',
      framesMax: 4,
    },
    death: {
      imageSrc: '/fightgame/images/Legacy/dead.png',
      framesMax: 8,
    },
  },
  attackBox: {
    offset: {
      x: 110,
      y: 50,
    },
    width: 50,
    height: 100,
  },
});

const keys = {
  a: {
    pressed: false,
  },
  d: {
    pressed: false,
  },
  w: {
    pressed: false,
  },

  ArrowLeft: {
    pressed: false,
  },
  ArrowRight: {
    pressed: false,
  },
  ArrowUp: {
    pressed: false,
  },
};

decreateTimer();

function animate() {
  window.requestAnimationFrame(animate);
  c.fillStyle = 'black';
  c.fillRect(0, 0, canvas.width, canvas.height);

  background.update();

  c.fillStyle = 'rgba(255,255,255,0.05)';
  c.fillRect(0, 0, canvas.width, canvas.height);
  player.update();
  enemy.update();

  player.velocity.x = 0;
  enemy.velocity.x = 0;

  //player

  if (keys.a.pressed && player.lastkey === 'a') {
    player.velocity.x = -3;
    player.switchSprite('run');
  } else if (keys.d.pressed && player.lastkey === 'd') {
    player.velocity.x = 3;
    player.switchSprite('run');
  } else {
    player.switchSprite('idle');
  }
  //jumping
  if (player.velocity.y < 0) {
    player.switchSprite('jump');
  } else if (player.velocity.y > 0) {
    player.switchSprite('fall');
  }
  if (keys.ArrowLeft.pressed && enemy.lastkey === 'ArrowLeft') {
    //enemy
    enemy.velocity.x = -3;
    enemy.switchSprite('run');
  } else if (keys.ArrowRight.pressed && enemy.lastkey === 'ArrowRight') {
    enemy.velocity.x = 3;
    enemy.switchSprite('run');
  } else {
    enemy.switchSprite('idle');
  }
  if (enemy.velocity.y < 0) {
    enemy.switchSprite('jump');
  } else if (enemy.velocity.y > 0) {
    enemy.switchSprite('fall');
  }
  //detect for collision
  if (
    rectangularCollision({
      rectangle1: player,
      rectangule2: enemy,
    }) &&
    player.isAttacking &&
    player.framesCurrent === 4
  ) {
    enemy.takeHit();
    player.isAttacking = false;
    gsap.to('#enemyHealth', {
      width: enemy.health + '%',
    });
  }

  //player miss tacking
  if (player.isAttacking && player.framesCurrent === 4) {
    player.isAttacking = false;
  }

  if (
    rectangularCollision({
      rectangle1: enemy,
      rectangule2: player,
    }) &&
    enemy.isAttacking &&
    enemy.framesCurrent === 2
  ) {
    player.takeHit();
    enemy.isAttacking = false;
    gsap.to('#playerHealth', {
      width: player.health + '%',
    });
  }

  //enemy dismissed
  if (enemy.isAttacking && enemy.framesCurrent === 2) {
    enemy.isAttacking = false;
  }

  if (enemy.health <= 0 || player.health <= 0) {
    determineWinner({ player, enemy, timeID });
  }
}

animate();

window.addEventListener('keydown', (event) => {
  if (!player.dead) {
    switch (event.key) {
      case 'd':
        if (player.position.x > canvas.width - 50) {
          player.position.x = canvas.width - 100;
        }
        keys.d.pressed = true;
        player.lastkey = 'd';
        break;
      case 'a':
        if (player.position.x < 20) {
          player.position.x = 20;
        }
        keys.a.pressed = true;
        player.lastkey = 'a';
        break;
      case 'w':
        if (player.position.y < 100) {
          player.position.y = 0;
          player.velocity.y = 0;
        }
        player.velocity.y = -20;
        break;
      case 'b':
        player.attack();
        break;
    }
  }
  if (!enemy.dead) {
    switch (event.key) {
      case 'ArrowRight':
        if (enemy.position.x > canvas.width - 50) {
          enemy.position.x = canvas.width - 100;
        }
        keys.ArrowRight.pressed = true;
        enemy.lastkey = 'ArrowRight';
        break;
      case 'ArrowLeft':
        if (enemy.position.x < 20) {
          enemy.position.x = 20;
        }
        keys.ArrowLeft.pressed = true;
        enemy.lastkey = 'ArrowLeft';
        break;
      case 'ArrowUp':
        if (enemy.position.y < 100) {
          enemy.position.y = 0;
          enemy.velocity.y = 0;
        }
        enemy.velocity.y = -20;
        break;
      case '.':
        enemy.attack();
        break;
    }
  }
});

window.addEventListener('keyup', (event) => {
  switch (event.key) {
    case 'd':
      keys.d.pressed = false;
      break;
    case 'a':
      keys.a.pressed = false;
      break;
  }
  switch (event.key) {
    case 'ArrowRight':
      keys.ArrowRight.pressed = false;
      break;
    case 'ArrowLeft':
      keys.ArrowLeft.pressed = false;
      break;
  }
});

document.getElementById('retryBtn').addEventListener('click', function () {
  window.location.reload();
});
