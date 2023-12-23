const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day11.txt");
const MATRIX = INPUT_LIST.map((row) => row.split(""));

const resolvePartOne = () => {
  console.log(MATRIX)
  // expand rows
  for (let i = 0; i < MATRIX.length; i++) {
    if (MATRIX[i].indexOf("#") < 0) {
      MATRIX.splice(i, 0, MATRIX[i]);
      i++;
    }
  }
  // expand cols
  for (let i = 0; i < MATRIX[0].length; i++) {
    containsGalaxy = false;
    for (let j = 0; j < MATRIX.length; j++) {
      if (MATRIX[j][i] === "#") {
        containsGalaxy = true;
        break;
      }
    }
    if (!containsGalaxy) {
      for (let x = 0; x < MATRIX.length; x++) {
        MATRIX[x].splice(i, 0, ".");
      } 
      i++;
    }
  }

  let galaxies = [];
  for (let i = 0; i < MATRIX.length; i++) {
    for (let j = 0; j < MATRIX[i].length; j++) {
      if (MATRIX[i][j] === "#") {
        galaxies.push([j, i]);
      }
    }
  }

  let result = 0
  for (let i = 0; i < galaxies.length; i++) {
    for (let j = i + 1; j < galaxies.length; j++) {
      result += Math.abs(galaxies[i][0] - galaxies[j][0]) + Math.abs(galaxies[i][1] - galaxies[j][1]);
    }
  }

  return result;
}

const resolvePartTwo = () => {
  
}

console.log(resolvePartOne());
console.log(resolvePartTwo());
