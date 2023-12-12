const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day9.txt");

class Node {
  constructor(left, right, parent) {
    this.left = left;
    this.right = right;
    this.parent = parent;
  }
}

const buildNodes = (line) => {
  const values = line.split(" ").map(val => Number(val));
  let nodes = []; // leaf nodes
  for (let i = 0; i < values.length - 1; i++) {
    const val = values[i];
    const nextVal = values[i + 1];
    nodes.push(new Node(val, nextVal, nextVal - val))
  }

  let allNodes = []; // all levels in tree
  while (nodes.some(node => node.parent !== 0)) {
    let nextNodes = [];
    for (let i = 0; i < nodes.length - 1; i++) {
      const val = nodes[i].parent;
      const nextVal = nodes[i + 1].parent;
      nextNodes.push(new Node(val, nextVal, nextVal - val));
    }
    allNodes.push(nodes);
    nodes = nextNodes;
  }
  return allNodes;
}

const resolvePartOne = () => {
  let result = [];
  for (let line of INPUT_LIST) {
    const allNodes = buildNodes(line);
    let sum = allNodes.reduce((acc, nodeList) => acc + nodeList[nodeList.length - 1].parent, 0)
    sum += allNodes[0][allNodes[0].length - 1].right;
    result.push(sum);
  }
  return result.reduce((acc, val) => acc + val, 0);
}

const resolvePartTwo = () => {
  let result = [];
  for (let line of INPUT_LIST) {
    const allNodes = buildNodes(line).reverse();
    let sum = 0;
    if (allNodes.length === 1) {
      sum = allNodes[0][0].parent;
    }
    for (let i = 0; i < allNodes.length - 1; i++) {
      currLevel = allNodes[i];
      nextLevel = allNodes[i + 1];
      if (i === 0) {
        sum = nextLevel[0].parent - currLevel[0].parent;
      } else {
        sum = nextLevel[0].parent - sum;
      }
    }
    sum = allNodes[allNodes.length - 1][0].left - sum;
    result.push(sum);
  }
  return result.reduce((acc, val) => acc + val, 0);
}

console.log(resolvePartOne());
console.log(resolvePartTwo());
