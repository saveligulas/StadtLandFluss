export function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
      return parts.pop().split(';').shift();
    }
  }
export function extractGameIdFromUrlAtEnd(url) {
  const lastSegment = url.split('/').filter(Boolean).pop();
  const regex = /(\d+)(?:\?|$)/;
  const match = lastSegment.match(regex);
  
  if (match) {
    const gameId = match[1];
    return gameId;
  } else {
    return null;
  }
}