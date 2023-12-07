const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day7.txt");
const CARDS = ["2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"];
const CARDS_PART_2 = [ "J", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Q", "K", "A"];

const cardStrength = (card, cards) => cards.indexOf(card);

const resolveTask = (isPart2) => {
  // high card = 0, ..., five of a kind = 6
  let handTypes = {0: [], 1: [], 2: [], 3: [], 4: [], 5: [], 6: []};
  let handsRanked = [];
  for (let line of INPUT_LIST) {
    const hand = line.split(" ")[0];
    const bid = Number(line.split(" ")[1]);
    handTypes[handType(hand, isPart2)].push({ hand, bid });
  }

  Object.entries(handTypes).forEach(([type, hands]) => {
    if (hands.length === 0) {
      return;
    }
    const sorted = hands.sort((a, b) => compareFn(a.hand, b.hand, isPart2));
    handsRanked.push(...sorted);
  });
  return handsRanked.reduce((acc, hand, i) => acc + (i + 1) * hand.bid, 0);
}

const handType = (hand, isPart2) => {
  let cardCountMap = {};
  for (let card of hand) {
    cardCountMap[card] = (cardCountMap[card] || 0) + 1;
  }

  if (isPart2 && cardCountMap["J"]) {
    processJoker(cardCountMap);
  }

  return Object.entries(cardCountMap).reduce((acc, [key, value]) => {
    switch (Number(value)) {
      case 5: return acc + 6; // Five of a kind
      case 4: return acc + 5; // Four of a kind
      case 3: return acc + 3; // Three of a kind
      case 2: return acc + 1; // One pair
      case 1: return acc;     // High card
    }
  }, 0);
}

const processJoker = (map) => {
  const count = map["J"];
  delete map["J"];
  if (count === 5) {
    map["A"] = 5;
    return;
  }
  // add "J" count to max value card
  let arr = Object.values(map);
  let max = Math.max(...arr);
  let strongestCard;
  Object.entries(map).forEach(([key, value]) => {
    if (value === max && cardStrength(key, CARDS_PART_2) > cardStrength(strongestCard, CARDS_PART_2)) {
      strongestCard = key;
    }
  });
  map[strongestCard] += count;
}

const compareFn = (a, b, isPart2) => {
  for (let i = 0; i < a.length; i++) {
    const cardAStrength = cardStrength(a[i], isPart2 ? CARDS_PART_2 : CARDS);
    const cardBStrength = cardStrength(b[i], isPart2 ? CARDS_PART_2 : CARDS);
    if (cardAStrength === cardBStrength) {
      continue;
    }
    if (cardAStrength < cardBStrength) {
      return -1;
    } else {
      return 1;
    }
  }
  return 0;
}

console.log(resolveTask(false));
console.log(resolveTask(true));
