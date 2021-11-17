using System;
using System.Collections.Generic;

namespace BarcodeFAPI.Model
{
    public partial class ScannedLog
    {
        public ScannedLog(string barCode, DateTime createdDateTime)
        {
            BarCode = barCode;
            CreatedDateTime = createdDateTime;
        }

        public int Id { get; set; }
        public string BarCode { get; set; }
        public DateTime CreatedDateTime { get; set; }
    }
}
