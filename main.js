function Point() {
  this.suspects = [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
  this.refresh0 = false;
}

Point.prototype.set = function(value) {
  this.suspects = [ value ];
}

Point.prototype.exclude = function(value) {
  if (this.ok()) {
    return false;
  }

  for (let x = 0; x < this.suspects.length; x++) {
    if (this.suspects[x] === value) {
      this.suspects.splice(x, 1);
      return this.ok();
    }
  }

  return false;
}

Point.prototype.ok = function() {
  return this.suspects.length == 1;
}

function Sudoku(mat) {
  this.count = 0;
  this.points = [];
  for (let x = 0; x < 9; x++) {
    let line = [];
    this.points.push(line);
    for (let y = 0; y < 9; y++) {
      let point = new Point();
      if (mat[x][y] > 0) {
        point.set(mat[x][y]);
        this.count++;
      }
      line.push(point);
    }
  }
}

Sudoku.prototype.print = function() {
  for (let x = 0; x < 9; x++) {
    for (let y = 0; y < 9; y++) {
      console.log(x + 1, y + 1, this.points[x][y].suspects);
    }
  }
}

Sudoku.prototype.go = function() {
  this.refresh0();
  this.print();
}

Sudoku.prototype.refresh0 = function() {
  while (true) {
    let c = this.count;
    for (let x = 0; x < 9; x++) {
      for (let y = 0; y < 9; y++) {
        let p = this.points[x][y];
        if (p.ok() && !p.refresh0) {
          let v = p.suspects[0];
          for (let k = 0; k < 9; k++) {
          	if (this.points[k][y].exclude(v)) {
              this.count++;
          	}
          	if (this.points[x][k].exclude(v)) {
              this.count++;
          	}
          }

          let px = Math.floor(x / 3) * 3;
          let py = Math.floor(y / 3) * 3;
          for (let i = 0; i < 2; i++) {
            for (let j = 0; j < 2; j++) {
          	  if (this.points[px + i][py + j].exclude(v)) {
                this.count++;
          	  }
            }
          }
        }
      }
    }
    if (c === this.count) {
      break;
    }
  }
}

const sudoku = new Sudoku([
  [ 0, 0, 0, 1, 0, 0, 0, 0, 0 ],
  [ 0, 0, 0, 0, 2, 7, 0, 0, 0 ],
  [ 4, 0, 6, 0, 0, 0, 1, 0, 9 ],
  [ 0, 0, 0, 0, 0, 0, 9, 2, 5 ],
  [ 6, 0, 0, 0, 0, 0, 0, 0, 0 ],
  [ 3, 0, 0, 8, 1, 0, 0, 0, 0 ],
  [ 7, 0, 0, 0, 4, 0, 3, 0, 0 ],
  [ 0, 0, 4, 0, 0, 0, 0, 0, 7 ],
  [ 0, 8, 5, 0, 9, 0, 0, 0, 4 ] ]);

sudoku.go();
console.log(sudoku.count);
