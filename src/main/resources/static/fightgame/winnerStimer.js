function rectangularCollision({ rectangle1, rectangule2 }) {
  return (
    rectangle1.attackBox.position.x + rectangle1.attackBox.width >= rectangule2.position.x &&
    rectangle1.attackBox.position.x <= rectangule2.position.x + rectangule2.width &&
    rectangle1.attackBox.position.y + rectangle1.attackBox.height >= rectangule2.position.y &&
    rectangle1.attackBox.position.y <= rectangule2.position.y + rectangule2.height
  );
}

function determineWinner({ player, enemy, timeID }) {
  clearTimeout(timeID);
  document.querySelector('#CheckedTime').style.display = 'flex';
  if (player.health === enemy.health) {
    document.querySelector('#CheckedTime').innerHTML = 'Tie';
  } else if (player.health > enemy.health) {
    document.querySelector('#CheckedTime').innerHTML = 'Player 1 Win';
  } else if (player.health < enemy.health) {
    document.querySelector('#CheckedTime').innerHTML = 'Player 2 Win';
  }
}

let timer = 60;
let timeID;
function decreateTimer() {
  if (timer > 0) {
    timeID = setTimeout(decreateTimer, 1000);
    timer--;
    document.querySelector('#time').innerHTML = timer;
  }

  if (timer === 0) {
    determineWinner({ player, enemy, timeID });
  }
}
