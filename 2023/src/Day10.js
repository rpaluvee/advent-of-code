const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day10.txt");
const MATRIX = INPUT_LIST.map((row) => row.split(""));

const findStart = () => {
  let startPos = [];
  MATRIX.forEach((elem, y) => {
    const x = elem.indexOf("S");
    if (x >= 0) {
      startPos = [x, y];
    }
  });
  return startPos;
}

const move = {
  up: (position) => {
    position[1]--;
  },
  right: (position) => {
    position[0]++;
  },
  down: (position) => {
    position[1]++;
  },
  left: (position) => {
    position[0]--;
  },
};

const check = (position, moveFunction) => {
  const checkedPos = [...position];
  moveFunction(checkedPos);
  return checkedPos;
}

const process = () => {
  const start = findStart(INPUT_LIST);
  let previousPos = [...start];
  let currentPos = [...start];
  currentPos[0]++;
  let steps = 1;
  let loop = [[...currentPos]];

  while (!(currentPos[0] === start[0] && currentPos[1] === start[1])) {
    const checkedTile = MATRIX[currentPos[1]][currentPos[0]];
    switch (checkedTile) {
      case "|":
        if (JSON.stringify(check(previousPos, move.down)) === JSON.stringify(currentPos)) {
          move.down(previousPos);
          move.down(currentPos);
        } else {
          move.up(previousPos);
          move.up(currentPos);
        }
        break;
      case "-":
        if (JSON.stringify(check(previousPos, move.right)) === JSON.stringify(currentPos)) {
          move.right(previousPos);
          move.right(currentPos);
        } else {
          move.left(previousPos);
          move.left(currentPos);
        }
        break;
      case "L":
        if (JSON.stringify(check(previousPos, move.down)) === JSON.stringify(currentPos)) {
          move.down(previousPos);
          move.right(currentPos);
        } else {
          move.left(previousPos);
          move.up(currentPos);
        }
        break;
      case "J":
        if (JSON.stringify(check(previousPos, move.down)) === JSON.stringify(currentPos)) {
          move.down(previousPos);
          move.left(currentPos);
        } else {
          move.right(previousPos);
          move.up(currentPos);
        }
        break;
      case "7":
        if (JSON.stringify(check(previousPos, move.right)) === JSON.stringify(currentPos)) {
          move.right(previousPos);
          move.down(currentPos);
        } else {
          move.up(previousPos);
          move.left(currentPos);
        }
        break;
      case "F":
        if (JSON.stringify(check(previousPos, move.left)) === JSON.stringify(currentPos)) {
          move.left(previousPos);
          move.down(currentPos);
        } else {
          move.up(previousPos);
          move.right(currentPos);
        }
        break;
    }
    loop.push([...currentPos]);
    steps++;
  }
  console.log("PART 1: ", steps / 2);

  let counter = 0;
	MATRIX.forEach((row, y) => {
		let inside = false;
		row.forEach((tile, x) => {
			if (loop.some(e => e[0] === x && e[1] === y)) {
				if (["|", "F", "7"].includes(tile)) {
          inside = !inside;
				}
			} else if (inside) {
				counter++
			}
		})
	})
  console.log("PART 2: " + counter);
}

process();
