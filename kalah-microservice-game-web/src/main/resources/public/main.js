'use strict';

var usernamePage = document.querySelector('#username-page');
var gamePage = document.querySelector('#game-page');
var usernameForm = document.querySelector('#usernameForm');
var messageArea = document.querySelector('#messageArea');
var errorMessageArea = document.querySelector('#errorMessageArea');
var connectingElement = document.querySelector('.connecting');

var board1 = document.querySelector('#player1-board');
var board2 = document.querySelector('#player2-board');
var thisUserPlayer1 = false;

var stompClient = null;
var username = null;
var requestIds = [];
var boardId = null;
var playerId = null;

var colors = [
  '#2196F3', '#32c787', '#00BCD4', '#ff5652',
  '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];
var player1Pits = [
  '#pit-01',
  '#pit-02',
  '#pit-03',
  '#pit-04',
  '#pit-05',
  '#pit-06'
];
var player2Pits = [
  '#pit-08',
  '#pit-09',
  '#pit-10',
  '#pit-11',
  '#pit-12',
  '#pit-13'
];
function enablePlayer1BoardClicks() {
  'use strict';
  var arrayLength = player1Pits.length;
  for (var i = 0; i < arrayLength; i++) {
    var pit = document.querySelector(player1Pits[i]);
    var seeds = pit.innerText;
    if (seeds < 1) {
      continue;
    }
    pit.addEventListener('click', submitSelection);
  }
}
function enablePlayer2BoardClicks() {
  'use strict';
  var arrayLength = player2Pits.length;
  for (var i = 0; i < arrayLength; i++) {
    var pit = document.querySelector(player2Pits[i]);
    var seeds = pit.innerText;
    if (seeds < 1) {
      continue;
    }
    pit.addEventListener('click', submitSelection);
  }
}
function enableBoardClicks(message) {
  var playerWhoLastMoved = message.username;
  'use strict';
  if (!isValid(playerWhoLastMoved)) {
    return;
  }
  if (!isValid(username)) {
    return;
  }
  if (playerWhoLastMoved === username) {
    return;
  }
  if (Boolean(thisUserPlayer1)) {
    enablePlayer1BoardClicks();
  } else {
    enablePlayer2BoardClicks();
  }
}
function updateYourMoveLabel(playerIdToMove) {
  'use strict';
  if (!isValid(playerIdToMove)) {
    return;
  }
  if (!isValid(playerId)) {
    return;
  }

  var player1ToPlay = document.querySelector('#player1-to-play');
  var player2ToPlay = document.querySelector('#player2-to-play');
  if (Boolean(thisUserPlayer1)) {
    player1ToPlay.innerText = playerIdToMove === playerId ? 'To MOVE' : '';
    player2ToPlay.innerText = playerIdToMove === playerId ? '' : 'To MOVE';
  } else {
    player1ToPlay.innerText = playerIdToMove === playerId ? '' : 'To MOVE';
    player2ToPlay.innerText = playerIdToMove === playerId ? 'To MOVE' : '';
  }
}
function disableBoardClicks() {
  'use strict';
  disablePlayer1BoardClicks();
  disablePlayer2BoardClicks();
}
function disablePlayer1BoardClicks() {
  'use strict';
  var arrayLength = player1Pits.length;
  for (var i = 0; i < arrayLength; i++) {
    var pitNode = document.querySelector(player1Pits[i]);
    pitNode.removeEventListener('click', submitSelection);
  }
}
function disablePlayer2BoardClicks() {
  'use strict';
  var arrayLength = player2Pits.length;
  for (var i = 0; i < arrayLength; i++) {
    var pitNode = document.querySelector(player2Pits[i]);
    pitNode.removeEventListener('click', submitSelection);
  }
}
function connect(event) {
  'use strict';
  username = document.querySelector('#name').value.trim();
  if (username) {
    usernamePage.classList.add('hidden');
    gamePage.classList.remove('hidden');
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
  }
  event.preventDefault();
}

function createRequestId(username) {
  'use strict';
  let requestId = username + '_' + (window.performance.timing.navigationStart + window.performance.now()).toFixed(3);
  requestIds.push(requestId);
  return requestId;
}
function onAppError(erroMessage) {
  'use strict';
  usernamePage.classList.remove('hidden');
  gamePage.classList.add('hidden');
  appendErrorMessageArea(erroMessage.body);
}
function appendErrorMessageArea(message) {
  'use strict';
  var errorText = document.createTextNode(message);
  var lineBreak = document.createElement('br');
  errorMessageArea.appendChild(errorText);
  errorMessageArea.appendChild(lineBreak);
  console.log("Error " + errorText);
}
function onConnected() {
  'use strict';
  stompClient.subscribe("/user/queue/errors", onAppError);
  // Subscribe to the Public Channel
  stompClient.subscribe('/topic/public', onMessageReceived);
  // Tell your username to the server
  stompClient.send("/app/game.addPlayer",
          {},
          JSON.stringify({username: username, type: 'JOIN', requestId: createRequestId(username)})
          );
  connectingElement.classList.add('hidden');
}

function onError(error) {
  'use strict';
  usernamePage.classList.remove('hidden');
  gamePage.classList.add('hidden');
  connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
  connectingElement.style.color = 'red';
}

function onLeaveMessage(message) {
  'use strict';
//  console.log(":::: payload.body: " + JSON.stringify(message, null, 4));
}
function isValid(object) {
  'use strict';
  if (object === null) {
    return false;
  }
  if (typeof object === undefined) {
    return false;
  }
  return true;
}
function removeIfUnsedBoard(board, player) {
  'use strict';
  if (!isValid(player)) {
//    console.log(":::: player NOT valid");
    return false;
  }
  if (!isValid(player.username)) {
//    console.log(":::: player.username NOT valid. Found: " + player.username);
    return false;
  }
  if (player.username !== username) {
//    console.log(":::: player.username NOT matched: player.username:" + player.username + " username:" + username);
    return false;
  }
  playerId = player.playerId;
  if (isValid(board) && isValid(board.parentNode)) {
    board.parentNode.removeChild(board);
  }
  return true;
}
function getPlayerLabel(player) {
  'use strict';
  var label = 'Waiting for player to join...';
  if (!isValid(player)) {
    console.log(":::: player NOT valid");
    return label;
  }
  if (!isValid(player.username)) {
    console.log(":::: player.username NOT valid. Found: " + player.username);
    return label;
  }
//  console.log(":::: player valid: " + JSON.stringify(player, null, 4));
  label = player.username;
  return label;
}
function updateMessageArea(message) {
  'use strict';
  var messageElement = document.createElement('li');
  if (message.type === 'JOIN') {
    messageElement.classList.add('event-message');
    message.content = message.username + ' joined!';
  } else if (message.type === 'LEAVE') {
    messageElement.classList.add('event-message');
    message.content = message.username + ' left!';
  } else {
    return;
  }
  var textElement = document.createElement('p');
  var messageText = document.createTextNode(message.content);
  textElement.appendChild(messageText);
  messageElement.appendChild(textElement);
  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight;
}
function onJoinMessage(message) {
  'use strict';
  console.log(":::: onJoinMessage(message)");
  if (username === null) {
    console.log('username is null');
    return;
  }
  if (typeof username === undefined) {
    console.log('username is undefined');
    return;
  }
  if (message.board === null) {
    console.log('message.board is null');
    return;
  }
  if (typeof message.board === undefined) {
    console.log('message.board is undefined');
    return;
  }
  boardId = message.board.boardId;
  console.log(":::: boardId: " + boardId);

  if (board1 === null) {
    console.log(":::: player1Board is null");
  }
  if (typeof board1 === undefined) {
    console.log(":::: player1Board is undefined");
  }
  if (board2 === null) {
    console.log(":::: player2Board is null");
  }
  if (typeof board2 === undefined) {
    console.log(":::: player2Board is undefined");
  }
  var iamPlayerOne = removeIfUnsedBoard(board2, message.board.player1);
  var iamPlayerTwo = removeIfUnsedBoard(board1, message.board.player2);
  if (Boolean(iamPlayerOne)) {
    thisUserPlayer1 = true;
    board1.classList.remove('hidden');
  }
  if (Boolean(iamPlayerTwo)) {
    thisUserPlayer1 = false;
    board2.classList.remove('hidden');
  }
  var iamLastToJoin = message.username === username;
  if (iamLastToJoin && isValid(message.board.player1) && isValid(message.board.player2)) {
    if (Boolean(thisUserPlayer1)) {
      enablePlayer1BoardClicks();
      updateYourMoveLabel(message.board.player1.playerId);
    } else {
      enablePlayer2BoardClicks();
      updateYourMoveLabel(message.board.player2.playerId);
    }
  }

  var player1Name = document.querySelector('#player1-name');
  var player2Name = document.querySelector('#player2-name');
  player1Name.innerText = getPlayerLabel(message.board.player1);
  player2Name.innerText = getPlayerLabel(message.board.player2);

  updateMessageArea(message);
}
function onPlayMessage(message) {
  'use strict';
//  console.log(":::: payload.body: " + JSON.stringify(message, null, 4));
  if (!isValid(boardId)) {
    console.log('board is not valid');
    return;
  }
  if (!isValid(message.board.boardId)) {
    console.log('boardId is not valid');
    return;
  }
  if (boardId !== message.board.boardId) {
    console.log('boardId NOT matched');
    return;
  }
  if (isValid(message.board.winnerPlayer)) {
    console.log('boardId NOT matched');
    updateWinner(message);
    return;
  }
  updateBoardCounts(message);
  enableBoardClicks(message);

  var playerWhoLastMoved = message.username;
  if (playerWhoLastMoved === message.board.player2.username) {
    updateYourMoveLabel(message.board.player1.playerId);
  } else {
    updateYourMoveLabel(message.board.player2.playerId);
  }
}
function updateWinner(message) {
  'use strict';
  if (message.board.winnerPlayer.playerId === playerId) {
    appendErrorMessageArea('You WON');
  } else {
    appendErrorMessageArea('You LOST');
  }
}
function updateBoardCounts(message) {
  'use strict';
  console.log('updateBoardCounts');
  for (var key in message.board.pits) {
    if (message.board.pits.hasOwnProperty(key)) {
      var value = message.board.pits[key];
//      console.log(key + " -> " + value);
      var pit = document.querySelector('#' + key);
      pit.innerText = value;
    }
  }
}
function onMessageReceived(payload) {
  'use strict';
//  console.log(":::: payload: " + JSON.stringify(payload, null, 4));
  var message = JSON.parse(payload.body);
  switch (message.type) {
    case 'LEAVE':
      window.console.log('message type: ' + message.type);
      onLeaveMessage(message);
      break;
    case 'JOIN':
      window.console.log('message type: ' + message.type);
      onJoinMessage(message);
      break;
    case 'PLAY':
      window.console.log('message type: ' + message.type);
      onPlayMessage(message);
      break;
    default:
      window.console.log('Unsupported message type: ' + message.type);
  }
}
function submitSelection(event) {
  'use strict';
  window.console.log('clicked');
  var pitId = event.target.id;
  var seeds = event.target.innerText;
  window.console.log('clicked by username:' + username + '. pitId: ' + pitId + " seeds: " + seeds);
  disableBoardClicks();
  if (stompClient) {
    var playerMoveMessage = {
      playerId: playerId,
      boardId: boardId,
      pitId: pitId,
      seeds: seeds,
      type: 'PLAY',
      requestId: createRequestId(username)
    };
    var payload = JSON.stringify(playerMoveMessage);
    console.log('SEND payload: ' + payload);
    stompClient.send("/app/game.playerMove", {}, payload);
  } else {
    appendErrorMessageArea('Error. Move not submitted to server. connection lost.');
  }
}
usernameForm.addEventListener('submit', connect, true);