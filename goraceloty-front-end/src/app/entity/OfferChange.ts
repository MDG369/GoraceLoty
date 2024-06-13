export class OfferChange {
  offerID: number;
  committedChange: string;
  commitType: string;
  createdAt: Date;

  constructor(offerID: number, committedChange: string, commitType: string, createdAt: Date) {
    this.offerID = offerID;
    this.committedChange = committedChange;
    this.commitType = commitType;
    this.createdAt = createdAt;
  }
}
