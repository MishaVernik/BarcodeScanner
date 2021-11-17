using System;
using System.Collections.Generic;
using BarcodeFAPI.Model;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace BarcodeFAPI.Context
{
    public partial class barcode_fapiContext : DbContext
    {
        public barcode_fapiContext()
        {
        }

        public barcode_fapiContext(DbContextOptions<barcode_fapiContext> options)
            : base(options)
        {
        }

        public virtual DbSet<GeneratedLog> GeneratedLogs { get; set; }
        public virtual DbSet<ScannedLog> ScannedLogs { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseSqlServer(Environment.GetEnvironmentVariable("SqlConnectionString"));
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<GeneratedLog>(entity =>
            {
                entity.Property(e => e.BarCode).HasColumnName("barCode");

                entity.Property(e => e.CreatedDateTime)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDateTime");
            });

            modelBuilder.Entity<ScannedLog>(entity =>
            {
                entity.Property(e => e.BarCode).HasColumnName("barCode");

                entity.Property(e => e.CreatedDateTime)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDateTime");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
