# ProofOfOwnership

PoW proof of concept based on the paper *Proofs of Ownership in Remote Storage Systems* by Shai Halevi, Danny Harnik, Benny Pinkas and Alexandra Shulman-Peleg.

Link to the paper: https://cyber.biu.ac.il/wp-content/uploads/2016/06/207.pdf

#### Context
Two files are uploaded to the server (an original doc and an original pdf).

#### Program parameters
1. Path to file
2. File hash (real or deceiving one)

#### Possible outputs
- If an original file is upload along with its real hasg, the server will return the file (its hash will be shown) after going through PoW protocol.
- If an original file is uploaded along a wrong/deceiving hash, the server will upload the file (if it exists, the file will be uploaded, if not, the server won't do anything).
- If a manipulated file is uploaded along with a wrong hash, the server will upload the file (it will calculate the manipulated file hash).
- If a manipulated file is uploaded along with its hash, the server will upload the file.

#### License

Copyright 2018 Francisco Javier Rodrigo Ginés

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
